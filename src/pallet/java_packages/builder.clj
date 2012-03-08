(ns pallet.java-packages.builder
  (:require
   [clojure.java.io :as io])
  (:use
   [pallet.action :only [as-clj-action]]
   [pallet.action.exec-script :only [exec-checked-script]]
   [pallet.action.remote-file :only [remote-file with-remote-file]]
   [pallet.environment :only [get-for]]
   [pallet.crate.git :only [git]]
   [pallet.crate.package-builder :only [deb-package-setup deb-build]]))

(def clone-url "git://github.com/rraptorr/sun-java6.git")

(defn builder
  [session]
  (->
   session
   deb-package-setup
   git))

(defn build-deb
  [session]
  (let [env (get-for session :sun-java6)
        version (:version env 31)
        release (:release env 1)
        dir (format "sun-java6-6.%s" version)]
    (->
     session
     (exec-checked-script
      "checkout rraptorr / sun-java6"
      (if (directory? ~dir)
        (do
          (cd ~dir)
          (git pull)
          (chain-or
           (git checkout ~(format "v6.%s-%s" version release))
           (exit 1))
          (cd -))
        (do
          (git clone ~clone-url ~dir)
          (cd ~dir)
          (chain-or
           (git checkout ~(format "v6.%s-%s" version release))
           (exit 1))
          (cd -))))
     (remote-file
      (format "%s/jdk-6u%s-linux-i586.bin" dir version)
      :local-file (format "jdk-6u%s-linux-i586.bin" version))
     (remote-file
      (format "%s/jdk-6u%s-linux-x64.bin" dir version)
      :local-file (format "jdk-6u%s-linux-x64.bin" version))
     (deb-build :dir dir :binary-only true)
     (exec-checked-script
      "build sun-java6-debs.tar.gz"
      (tar cvfz "sun-java6-debs.tar.gz" "sun-java6*.deb"))
     (with-remote-file
       (as-clj-action
        (fn [session local-path]
          (io/copy local-path (io/file "sun-java6-debs.tar.gz"))
          session)
        [session local-path])
       "sun-java6-debs.tar.gz"))))

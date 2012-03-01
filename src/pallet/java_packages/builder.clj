(ns pallet.java-packages.builder
  (:require
   [clojure.java.io :as io])
  (:use
   [pallet.action :only [as-clj-action]]
   [pallet.action.exec-script :only [exec-checked-script]]
   [pallet.action.remote-file :only [remote-file with-remote-file]]
   [pallet.crate.git :only [git]]
   [pallet.crate.package-builder :only [deb-package-setup deb-build]]))

(def clone-url "git://github.com/rraptorr/sun-java6.git")
(def version 30)
(def release 3)

(defn builder
  [session]
  (->
   session
   deb-package-setup
   git))

(defn build-deb
  [session]
  (let [dir (format "sun-java6-6.%s" version)]
    (->
     session
     (exec-checked-script
      "checkout rraptorr / sun-java6"
      (if (directory? ~dir)
        (do
          (cd ~dir)
          (git pull)
          (git checkout )
          (cd ..))
        (git clone ~clone-url ~dir)))
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

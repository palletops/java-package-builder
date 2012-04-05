(ns pallet.task.builddeb
  "Build java6 deb"
  (:use
   [pallet.core :only [converge]]
   [pallet.java-packages.nodes :only [pkgr-group]]))


(defn builddeb
  "Build sun-java6 debs. You can specify the java build version (the number
  after the u) and the patch level (e.g 1) for the tag to use rraptorr's
  sun-java6 repo."
  [request & [version & [release & _]]]
  (let [converge-args (apply concat
                             (->
                              request
                              (dissoc :config :project)
                              (assoc :environment
                                (merge
                                 (-> request :project :environment)
                                 {:sun-java6 {:version (or version 31)
                                              :release (or release 1)}}))))]
    (apply converge {(pkgr-group) 1}
           :phase [:configure :package-java]
           converge-args)
    (apply converge {(pkgr-group) 0} converge-args)))

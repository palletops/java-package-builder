(ns pallet.task.builddeb
  "Build java6 deb"
  (:use
   [pallet.core :only [converge]]
   [pallet.java-packages.nodes :only [pkgr-group]]))


(defn builddeb
  "Build RPMS"
  [request & [version & [release & _]]]
  (let [converge-args (apply concat
                             (->
                              request
                              (dissoc :config :project)
                              (assoc :environment
                                (merge
                                 (-> request :project :environment)
                                 :sun-java6 {:version version
                                             :release release}))))]
    (apply converge {pkgr-group 1}
           :phase [:configure :package-java]
           converge-args)
    (apply converge {pkgr-group 0} converge-args)))

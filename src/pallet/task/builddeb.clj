(ns pallet.task.builddeb
  "Build java6 deb"
  (:use
   [pallet.core :only [converge]]
   [pallet.java-packages.nodes :only [pkgr-group]]))


(defn builddeb
  "Build RPMS"
  [request & args]
  (let [converge-args (apply concat
                             (->
                              request
                              (dissoc :config :project)
                              (assoc :environment
                                (-> request :project :environment))))]
    (apply converge {pkgr-group 1}
           :phase [:configure :package-java]
           converge-args)
    (apply converge {pkgr-group 0} converge-args)))

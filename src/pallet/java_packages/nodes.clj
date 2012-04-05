(ns pallet.java-packages.nodes
  (:use
   [pallet.core :only [server-spec group-spec node-spec]]
   [pallet.crate.automated-admin-user :only [automated-admin-user]]
   [pallet.java-packages.builder :only [builder build-deb]]))


;; Package builder node
(defn pkgr-node
  []
  (node-spec
   :image {:os-family :debian
           :os-version-matches "6.0.2.1"
           :os-64-bit true}))

(defn pkgr-server
  []
  (server-spec
   :phases {:bootstrap automated-admin-user
            :configure builder
            :package-java build-deb}))

(defn pkgr-group
  []
  (group-spec "pkgr" :node-spec (pkgr-node) :extends [(pkgr-server)]))

(defproject java-package-builder "0.1.0-SNAPSHOT"
  :description "Builds packages of java"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.cloudhoist/pallet "0.7.0-SNAPSHOT"]
                 [org.cloudhoist/pallet-vmfest "0.2.0-SNAPSHOT"]
                 [org.cloudhoist/git "0.5.0"]
                 [org.cloudhoist/package-builder "0.6.0-SNAPSHOT"]
                 [org.slf4j/slf4j-api "1.6.1"]
                 [ch.qos.logback/logback-core "1.0.0"]
                 [ch.qos.logback/logback-classic "1.0.0"]]
  :dev-dependencies [[org.cloudhoist/pallet-lein "0.4.2-SNAPSHOT"]]
  :repositories {"sonatype"
                 "https://oss.sonatype.org/content/repositories/releases/"})

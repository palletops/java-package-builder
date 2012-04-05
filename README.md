# java-package-builder

A project to build sun java6 debs.

## Usage

* `lein plugin install org.cloudhoist/pallet-lein 0.4.2-SNAPSHOT`
* `lein pallet add-service vmfest`
* `lein pallet -P vmfest add-vmfest-image https://s3.amazonaws.com/vmfest-images/debian-6.0.2.1-64bit-v0.3.vdi.gz`
* `git clone git://github.com/palletops/java-package-builder.git`
* `cd java-package-builder`
* Download jdk-6u31-linux-i586.bin and jdk-6u31-linux-x64.bin from Oracle into
  the java-package-builder directory
* `lein pallet -P vmfest builddeb`

This will fire up a virtualbox vm, create the deb packages, and put them in the
`sun-java6-debs.tar.gz` file, closing the vm afterwards.

You can specify the java build number and the sun-java6 patch level in
[rraptorr's](https://github.com/rraptorr/sun-java6) repository.

* `lein pallet -P vmfest builddeb 31 1`

## License

Copyright © 2012 Hugo Duncan

Distributed under the Eclipse Public License.

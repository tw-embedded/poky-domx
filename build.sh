
source ./oe-init-build-env

bitbake-layers add-layer ../meta-openembedded/meta-oe
bitbake-layers add-layer ../meta-openembedded/meta-filesystems
bitbake-layers add-layer ../meta-openembedded/meta-python
bitbake-layers add-layer ../meta-openembedded/meta-networking
bitbake-layers add-layer ../meta-virtualization

echo "" >> conf/local.conf
echo "MACHINE = \"fake-arm64\"" >> conf/local.conf
echo "DISTRO = \"poky\"" >> conf/local.conf
echo "IMAGE_FSTYPES += \"cpio.gz\"" >> conf/local.conf
echo "DISTRO_FEATURES += \" virtualization xen\"" >> conf/local.conf
echo "BUILD_REPRODUCIBLE_BINARIES = \"1\"" >> conf/local.conf
echo "" >> conf/local.conf

bitbake fake-dom0


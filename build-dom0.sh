
source ./oe-init-build-env

bitbake-layers add-layer ../meta-openembedded/meta-oe
bitbake-layers add-layer ../meta-openembedded/meta-filesystems
bitbake-layers add-layer ../meta-openembedded/meta-python
bitbake-layers add-layer ../meta-openembedded/meta-networking
bitbake-layers add-layer ../meta-virtualization

sed -i '/appended by alix/,$d' conf/local.conf

cat >> conf/local.conf << EOF
#### appended by alix ####
MACHINE = "fake-arm64"
DISTRO = "poky"
IMAGE_FSTYPES += "cpio.gz"
DISTRO_FEATURES += " virtualization xen systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"
BUILD_REPRODUCIBLE_BINARIES = "1"
#EXTRA_IMAGEDEPENDS:remove = " qemu-native"
#IMAGE_INSTALL:remove = " qemu-native"
EOF

bitbake fake-dom0
ls ./build/tmp/deploy/images/fake-arm64/fake-dom0-fake-arm64.cpio.gz

GREEN='\033[32m'
NC='\033[0m'

echo -e ${GREEN}"start build fake-dom0 sdk..."${NC}
bitbake -c populate_sdk fake-dom0
ls build/tmp/deploy/sdk 


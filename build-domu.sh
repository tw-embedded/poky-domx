
source ./oe-init-build-env build-domu

bitbake-layers add-layer ../meta-openembedded/meta-oe
bitbake-layers add-layer ../meta-openembedded/meta-filesystems
bitbake-layers add-layer ../meta-openembedded/meta-python
bitbake-layers add-layer ../meta-openembedded/meta-networking
bitbake-layers add-layer ../meta-baize

sed -i '/appended by alix/,$d' conf/local.conf

cat >> conf/local.conf << EOF
#### appended by alix ####
MACHINE = "fake-arm64"
DISTRO = "poky"
IMAGE_FSTYPES += "cpio.gz"
DISTRO_FEATURES += " systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"
BUILD_REPRODUCIBLE_BINARIES = "1"
EOF

bitbake baize-domu

#bitbake -c populate_sdk baize-domu
#tmp/work/fake_arm64-poky-linux/baize-domu/1.0-r0/deploy-baize-domu-image-complete/baize-domu-fake-arm64.cpio.gz
#tmp/deploy/sdk/poky-glibc-x86_64-baize-domu-cortexa57-fake-arm64-toolchain-4.1.2.sh


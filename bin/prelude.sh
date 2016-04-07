#!/bin/bash -e
#
# Copyright (c) 2015, Robby, Kansas State University
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# 1. Redistributions of source code must retain the above copyright notice, this
#    list of conditions and the following disclaimer.
# 2. Redistributions in binary form must reproduce the above copyright notice,
#    this list of conditions and the following disclaimer in the documentation
#    and/or other materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
# ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
# ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
# ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
COMMANDS="wget unzip rm mv git"
for COMMAND in ${COMMANDS}; do
	type -P ${COMMAND} &>/dev/null && continue || { >&2 echo "${COMMAND} command not found."; exit 1; }
done
ZULU_VERSION=8.13.0.5-jdk8.0.72
SBT_VERSION=0.13.11
NODE_VERSION=5.10.1
Z3_VERSION=4.4.1
if [ -z "${PLATFORM}" ]; then
  if [ -n "$COMSPEC" -a -x "$COMSPEC" ]; then
    PLATFORM=win
  elif [ "$(uname)" == "Darwin" ]; then
    PLATFORM=mac
  elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    PLATFORM=linux
  fi
fi
if [ "${PLATFORM}" = "win"  ]; then
  ZULU_DROP_URL=http://cdn.azul.com/zulu/bin/zulu${ZULU_VERSION}-win_x64.zip
  NODE_DROP_URL=https://nodejs.org/dist/v${NODE_VERSION}/win-x64/node.exe
  Z3_DROP_URL=https://github.com/Z3Prover/bin/raw/master/releases/z3-${Z3_VERSION}-x64-win.zip
elif [ "${PLATFORM}" = "mac"  ]; then
  ZULU_DROP_URL=http://cdn.azul.com/zulu/bin/zulu${ZULU_VERSION}-macosx_x64.zip
  NODE_DROP_URL=https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-darwin-x64.tar.gz
  Z3_DROP_URL=https://github.com/Z3Prover/bin/raw/master/releases/z3-${Z3_VERSION}-x64-osx-10.11.zip
elif [ "${PLATFORM}" = "linux"  ]; then
  type -P xz &>/dev/null || { >&2 echo "xz command not found."; exit 1; }
  ZULU_DROP_URL=http://cdn.azul.com/zulu/bin/zulu${ZULU_VERSION}-linux_x64.tar.gz
  NODE_DROP_URL=https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-x64.tar.xz
  Z3_DROP_URL=https://github.com/Z3Prover/bin/raw/master/releases/z3-${Z3_VERSION}-x64-ubuntu-14.04.zip
else
  >&2 echo "Sireum does not support: $(uname)."
  exit 1
fi
REPO=$( cd "$( dirname "$0" )"/.. &> /dev/null && pwd )
mkdir -p ${REPO}/platform
cd ${REPO}/platform
ZULU_DROP="${ZULU_DROP_URL##*/}"
ZULU_DIR="${ZULU_DROP%.*}"
if [[ ${ZULU_DIR} == *.tar ]]; then
  ZULU_DIR="${ZULU_DIR%.*}"
fi
grep -q ${ZULU_VERSION} java/VER &> /dev/null && ZULU_UPDATE=false || ZULU_UPDATE=true
if [ ! -d "java" ] || [ "${ZULU_UPDATE}" = "true" ]; then
  if [ ! -f ${ZULU_DROP} ]; then
    echo "Please wait while downloading Zulu JDK ${ZULU_VERSION} ..."
    wget -q ${ZULU_DROP_URL}
    echo
  fi
  if [[ ${ZULU_DROP} == *.zip ]]; then
    unzip -oq ${ZULU_DROP}
  else
    tar xf ${ZULU_DROP}
  fi
  rm ${ZULU_DROP}
  rm -fR java
  mv ${ZULU_DIR} java
  if [ -d "java/bin" ]; then
    echo "${ZULU_VERSION}" > java/VER
  else
    >&2 echo "Could not install Zulu JDK ${ZULU_VERSION}."
    exit 1
  fi
fi
mkdir -p ${REPO}/apps
cd ${REPO}/apps
Z3_DROP="${Z3_DROP_URL##*/}"
Z3_DIR="${Z3_DROP%.*}"
grep -q ${Z3_VERSION} z3/VER &> /dev/null && Z3_UPDATE=false || Z3_UPDATE=true
if [ ! -d "z3" ] || [ "${Z3_UPDATE}" = "true" ]; then
  if [ ! -f ${Z3_DROP} ]; then
    echo "Please wait while downloading Z3 ${Z3_VERSION} ..."
    wget -q ${Z3_DROP_URL}
    echo
  fi
  unzip -oq ${Z3_DROP}
  rm ${Z3_DROP}
  rm -fR z3
  mv ${Z3_DIR} z3
  if [ -d "z3/bin" ]; then
    echo "${Z3_VERSION}" > z3/VER
  else
    >&2 echo "Could not install Z3 ${Z3_VERSION}."
    exit 1
  fi
fi
if [ "${DISTROS}" = "true" ]; then
  exit
fi
cd ${REPO}/platform
SBT_DROP_URL=https://dl.bintray.com/sbt/native-packages/sbt/${SBT_VERSION}/sbt-${SBT_VERSION}.zip
SBT_DROP="${SBT_DROP_URL##*/}"
grep -q ${SBT_VERSION} sbt/VER &> /dev/null && SBT_UPDATE=false || SBT_UPDATE=true
if [ ! -d "sbt" ] || [ "${SBT_UPDATE}" = "true" ]; then
  if [ ! -f ${SBT_DROP} ]; then
    echo "Please wait while downloading sbt ${SBT_VERSION} ..."
    wget -q ${SBT_DROP_URL}
    echo
  fi
  rm -fR sbt
  unzip -oq ${SBT_DROP}
  rm ${SBT_DROP}
  if [ -d "sbt/bin" ]; then
    echo "${SBT_VERSION}" > sbt/VER
  else
    >&2 echo "Could not install sbt ${SBT_VERSION}."
    exit 1
  fi
fi
grep -q ${NODE_VERSION} node/VER &> /dev/null && NODE_UPDATE=false || NODE_UPDATE=true
if [ ! -d "node" ] || [ "${NODE_UPDATE}" = "true" ]; then
  if [ "${PLATFORM}" = "win" ]; then
    NODE_DROP="${NODE_DROP_URL##*/}"
    rm -fR node
    mkdir -p node/bin
    cd node/bin
    echo "Please wait while downloading Node.js ${NODE_VERSION} ..."
    wget -q ${NODE_DROP_URL}
    echo
    cd ../..
    if [ -d "node/bin" ]; then
      echo "${NODE_VERSION}" > node/VER
    else
      >&2 echo "Could not install Node.js ${NODE_VERSION}."
      exit 1
    fi
  else
    NODE_DROP="${NODE_DROP_URL##*/}"
    NODE_DIR="${NODE_DROP%.tar.*}"
    if [ ! -f ${NODE_DROP} ]; then
      echo "Please wait while downloading Node.js ${NODE_VERSION} ..."
      wget -q ${NODE_DROP_URL}
      echo
    fi
    tar xf ${NODE_DROP}
    rm ${NODE_DROP}
    rm -fR node
    mv ${NODE_DIR} node
    if [ -d "node/bin" ]; then
      echo "${NODE_VERSION}" > node/VER
    else
      >&2 echo "Could not install Node.js ${NODE_VERSION}."
      exit 1
    fi
  fi
fi

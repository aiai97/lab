#!/opt/homebrew/bin/bash

set -euo pipefail

# 模拟当前主机名
host="hkl79.hk.hsbc"
echo "Test script started on host: $host"

# 声明关联数组（注意：bash 4+）
declare -A host_dirs
host_dirs["hkl79.hk.hsbc"]="/tmp/test_sim_dir_1 /tmp/test_sim_dir_2"
host_dirs["shz22.sh.hsbc"]="/tmp/sh_dir_1 /tmp/sh_dir_2"
host_dirs["nyc88.us.hsbc"]="/tmp/ny_dir_1 /tmp/ny_dir_2"

# 提取对应 host 的目录
dirs="${host_dirs[$host]}"

if [ -z "$dirs" ]; then
  echo "No directories mapped for host: $host"
  exit 1
fi

echo "Mapped directories for $host: $dirs"

for dir in $dirs; do
  echo "Processing directory: $dir"
  mkdir -p "$dir"
done

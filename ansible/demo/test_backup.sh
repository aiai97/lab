#!/opt/homebrew/bin/bash

set -euo pipefail

log_info()  { echo "$(date +'%F %T') [INFO] $*"; }
log_error() { echo "$(date +'%F %T') [ERROR] $*" >&2; }

rollback() {
  log_error "Rollback triggered."
  for d in "${processed_copies[@]:-}"; do
    if [[ -d "$d" ]]; then
      log_info "Deleting copied directory $d"
      rm -rf "$d" || log_error "Failed to delete $d during rollback"
    fi
  done
  for f in "${compressed_files[@]:-}"; do
    if [[ -f "$f" ]]; then
      log_info "Deleting compressed file $f"
      rm -f "$f" || log_error "Failed to delete $f during rollback"
    fi
  done
  log_error "Rollback complete."
  exit 1
}

trap 'rollback' ERR

# 模拟主机名
host=$(hostname -f)
log_info "Test script started on host: $host"

# 这里为模拟测试，把主机名映射为/tmp下的测试目录
declare -A host_dirs
host_dirs["$(hostname -f)"]="/tmp/test_sim_dir_1 /tmp/test_sim_dir_2"

dirs=${host_dirs[$host]:-}

if [[ -z "$dirs" ]]; then
  log_error "No directories configured for host $host"
  exit 1
fi

log_info "Directories to process: $dirs"

processed_copies=()
compressed_files=()

# 创建模拟目录和测试文件（如果不存在）
for dir in $dirs; do
  if [[ ! -d "$dir" ]]; then
    log_info "Creating simulated directory $dir"
    mkdir -p "$dir"
    echo "This is a test file in $dir" > "$dir/testfile.txt"
  fi
done

# 检查目录是否被占用 (macOS 同样支持 lsof)
for dir in $dirs; do
  if lsof +D "$dir" > /dev/null 2>&1; then
    log_error "Directory $dir is currently in use"
    exit 1
  fi
done
log_info "All directories exist and are free to use."

# 复制和压缩目录
for dir in $dirs; do
  base=$(basename "$dir")
  timestamp=$(date +%Y%m%d%H%M%S)
  copy_dir="/tmp/test_copy_${base}_${timestamp}"
  tar_file="/tmp/test_archive_${base}_${timestamp}.tar.gz"

  log_info "Copying $dir to $copy_dir"
  cp -a "$dir" "$copy_dir"
  processed_copies+=("$copy_dir")

  log_info "Compressing $copy_dir to $tar_file"
  tar -czf "$tar_file" -C "$(dirname "$copy_dir")" "$(basename "$copy_dir")"
  compressed_files+=("$tar_file")
done
# 重命名原目录，加后缀 "_toDeleted"
for dir in $dirs; do
  target="${dir}_toDeleted"
  log_info "Renaming original directory $dir to $target"
  mv "$dir" "$target"
done

# 删除复制的临时目录
for copy_dir in "${processed_copies[@]}"; do
  log_info "Deleting copied directory $copy_dir"
  rm -rf "$copy_dir"
done

log_info "Test script completed successfully. No real files were modified."
exit 0

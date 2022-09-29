#!/bin/bash
if sudo test -f /data001/oddiadm/user/script/bin.sh;
then
  sudo chmod -R +x /data001/oddiadm/user/*;
  sudo /data001/oddiadm/user/script/bin.sh start;
fi


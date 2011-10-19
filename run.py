import subprocess as sub
import psutil
import time

proc_name = "javaw.exe"

while(True):
   proc = sub.Popen(['run.bat'])

   time.sleep(3600)
   for p in psutil.process_iter():
      if p.name == proc_name:
         p.terminate()

   time.sleep(10)



   



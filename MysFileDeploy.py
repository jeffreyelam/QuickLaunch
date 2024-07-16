import os
import os.path
import subprocess
import io
import sys

filepath = ''
mergecommand = ''

for i, arg in enumerate(sys.argv):
	if i == 1:
		filepath = arg
	elif i == 2:
		mergecommand = arg

def openWinmergeFiles(environment,filePath):
	environmentPaths = [0] * 5
	environmentPaths[0] = {'path': "c:\\inetpub\\wwwroot\\"}
	environmentPaths[1] = {'path': "\\\\amznfsxbl1a2mn7.mysweb.net\\share\\Development\\"}
	environmentPaths[2] = {'path': "\\\\amznfsxsffi9ljm.mysweb.net\\share\\Test\\"}
	environmentPaths[3] = {'path': "\\\\amznfsxpms4nnk0.mysweb.net\\share\\Staging\\"}
	environmentPaths[4] = {'path': "\\\\amznfsxotr4sl7t.mysweb.net\\share\\Production\\"}


	##Populate this way for formatting, easier to read this way

	if environment == "localtodev":
		environmentFile = [0] * 1
		environmentFile[0] = {'fileName': 'ctodev.winmerge', 'index': 0, 'path1': "c:\\inetpub\\wwwroot\\", 'path2': "\\\\amznfsxbl1a2mn7.mysweb.net\\share\\Development\\"}
	if environment == "devtotest":
		environmentFile = [0] * 1
		environmentFile[0] = {'fileName': 'devtotest.winmerge', 'index': 0, 'path1': "\\\\amznfsxbl1a2mn7.mysweb.net\\share\\Development\\", 'path2': "\\\\amznfsxsffi9ljm.mysweb.net\\share\\Test\\"}
	if environment == "testtostage":
		environmentFile = [0] * 1
		environmentFile[0] = {'fileName': 'testtostage.winmerge', 'index': 0, 'path1': "\\\\amznfsxsffi9ljm.mysweb.net\\share\\Test\\", 'path2': "\\\\amznfsxpms4nnk0.mysweb.net\\share\\Staging\\"}
	if environment == "stagetoprod":
		environmentFile = [0] * 1
		environmentFile[0] = {'fileName': 'stagetoprod.winmerge', 'index': 0, 'path1': "\\\\amznfsxpms4nnk0.mysweb.net\\share\\Staging\\", 'path2': "\\\\amznfsxotr4sl7t.mysweb.net\\share\\Production\\"}
	for environment in environmentPaths:
		filePath = filePath.lower().replace(environment['path'].lower(),"")
		##Create winmerge files using 5 environments and local.filePath
	for environment in environmentFile:
		fileContents = ''
		if environment['index'] < 1:
			fileContents += '<?xml version="1.0" encoding="UTF-8"?>\n\n'
			fileContents += '<project>\n'
			fileContents += '\t<paths>\n'
			fileContents += '\t\t<left>' + environmentFile[environment['index']]['path1'] + filePath + '</left>\n'
			fileContents += '\t\t<right>' + environmentFile[environment['index']]['path2'] + filePath + '</right>\n'
			fileContents += '\t\t<filter>Exclude Garbage Files</filter>\n'
			fileContents += '\t\t<subfolders>0</subfolders>\n'
			fileContents += '\t\t<left-readonly>0</left-readonly>\n'
			fileContents += '\t\t<right-readonly>0</right-readonly>\n'
			fileContents += '\t</paths>\n'
			fileContents += '</project>'
			with io.FileIO(environment['fileName'], "w") as file:
				file.write(bytes(fileContents, 'UTF-8'))

	##Open all the created winmerge files (prod merge default to off)
	for environment in environmentFile:
		if environment['index'] < 1:
			os.startfile(environment['fileName'])
	##Delete all the newly created winmerge files
	##close program

openWinmergeFiles(mergecommand, filepath)
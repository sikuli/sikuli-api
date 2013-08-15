import sys
sys.path.append("sikuli-api-1.0.2-standalone.jar")

from org.sikuli.api import DesktopScreenRegion
from org.sikuli.api import ImageTarget
from org.sikuli.api import API;
from org.sikuli.api.robot.desktop import DesktopMouse

from java.net import URL

def find(target):
	t = ImageTarget(URL(target))
	d = DesktopScreenRegion()
	x = d.find(t)
	return x

def wait(target):
	t = ImageTarget(URL(target))
	d = DesktopScreenRegion()
	x = d.wait(t,5000)
	return x
	
def click(screenRegion):
	m = DesktopMouse()
	m.click(screenRegion.getCenter())
		
def browse(url):
	API.browse(URL(url))
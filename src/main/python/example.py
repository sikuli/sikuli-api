from sikuli import *

browse("http://code.google.com/")
x = wait("http://code.google.com/images/code_logo.gif")
click(x)
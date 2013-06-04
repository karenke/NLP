import xml.dom.minidom
from xml.dom.minidom import Node
dom = xml.dom.minidom.parse('Dictionary.xml')
itemlist = dom.getElementsByTagName('lexelt') 
print len(itemlist)
print itemlist[0].attributes['name'].value
for s in itemlist :
    print s.attributes['name'].value

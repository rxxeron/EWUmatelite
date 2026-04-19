import xml.etree.ElementTree as ET
import re

tree = ET.parse(r'C:\Users\Farzana Safa\Downloads\Academic Repository Task-2026-04-13-202215.svg')
root = tree.getroot()
namespace = {'svg': 'http://www.w3.org/2000/svg'}

text_nodes = []
for node in root.iter():
    if node.text and node.text.strip():
        text_nodes.append(node.text.strip())

print(' | '.join(text_nodes[:500]))

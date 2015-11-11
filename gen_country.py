# Generates xml file with localized countries names for Android in resources directory
# Usage:
#
# python ./gen_country.py <language_code>
#
# Example:
#
# python ./gen_country.py es - generates for spanish
#
# If language code is not specified default for English is used
#
# To install pycountry module run `pip install pycountry`
import pycountry
import gettext
import sys, os

def _mkdir_recursive(path):
    sub_path = os.path.dirname(path)
    if not os.path.exists(sub_path):
        _mkdir_recursive(sub_path)
    if not os.path.exists(path):
        os.mkdir(path)

has_locale = len(sys.argv) > 1
if has_locale:
	code = sys.argv[1]
	try:
		locale = gettext.translation('iso3166', pycountry.LOCALES_DIR, languages=[str(code)])
	except IOError:
		print "Locale '{}' not found".format(code)
		sys.exit(2) 
	locale.install()
	print "Using locale '" + code + "'"
else:
	print "No locale specified, using default (English)"

values_dir = 'src/main/res/values'
if has_locale:
	filename = values_dir + "-" + code
else:
	filename = values_dir
_mkdir_recursive(filename)
filename += "/countries.xml"

f = open(filename, "w")
f.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
f.write("<!-- THIS IS GENERATED FILE. DO NOT EDIT. USE gen_country.py SCRIPT TO UPDATE THIS. -->\n")
f.write("<resources>\n")
for country in pycountry.countries:
	line = '<string name=\"country_name_{code}\">{name}</string>'
	if has_locale:
		name_in_locale = _(country.name)
		f.write(line.format(code = country.numeric, name = name_in_locale))
	else:
		f.write(line.format(code = country.numeric, name = country.name.encode('utf-8')))
	f.write('\n')
f.write("</resources>\n")
f.close()

print "Finished generating file",filename
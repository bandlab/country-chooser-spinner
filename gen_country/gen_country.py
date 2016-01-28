#!/usr/bin/env python
#
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
# To install dependencies run `pip install -r ./requirements.txt`
#
import gettext
import os
import pycountry
import sys

VALUES_DIR_PATH = 'src/main/res/values'


def main():
    # parse locale param
    has_locale = len(sys.argv) > 1

    # path to resource dir
    path = get_path(sys.argv[1] if has_locale else None)

    # create dirs if not exists
    if not os.path.exists(path):
        os.makedirs(path)

    # Check dir
    if not os.path.isdir(path):
        print "Target path is not directory: %s " % path
        sys.exit(1)

    # generate localized countries names file
    generate_countries_xml(path, has_locale)

    # generate countries codes array
    generate_countries_codes_array_xml(path)

    print "Finished generating file", path


def get_path(locale_code):
    path = VALUES_DIR_PATH
    if locale_code:
        try:
            locale = gettext.translation('iso3166', pycountry.LOCALES_DIR,
                                         languages=[str(locale_code)])
        except IOError:
            print "Locale %s not found" % locale_code
            sys.exit(1)

        locale.install()

        path += "-" + locale_code
        print "Using locale %s" % locale_code
    else:
        print "No locale specified, using default (English)"

    return path


def generate_countries_xml(path, has_locale):
    f = open(path + "/countries.xml", "w")
    write_resources_header(f)

    for country in pycountry.countries:
        f.write('<string name="country_name_{code}">{name}</string>\n'.format(
                code=country.numeric,
                name=get_country_name(country, has_locale)
        ))

    write_resources_footer(f)
    f.close()


def get_country_name(country, has_locale):
    return prepare_string(
            _(country.name) if has_locale else country.name.encode('utf-8')
    )


def generate_countries_codes_array_xml(path):
    f = open(path + "/country_codes_array.xml", "w")
    write_resources_header(f)

    f.write('<string-array translatable="false" name="country_codes">\n')

    for country in pycountry.countries:
        f.write("<item>%s</item>\n" % country.numeric)

    f.write("</string-array>\n")
    write_resources_footer(f)
    f.close()


def write_resources_header(file):
    file.write('<?xml version="1.0" encoding="utf-8"?>\n')
    file.write("<!-- THIS IS GENERATED FILE. DO NOT EDIT. "
               "USE gen_country.py SCRIPT TO UPDATE THIS. -->\n")
    file.write("<resources>\n")


def write_resources_footer(file):
    file.write("</resources>\n")


def prepare_string(text):
    """
    escapes characters for android xml resource file
    """
    return text.replace("'", r"\'")


if __name__ == "__main__":
    main()

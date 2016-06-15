# Country Chooser Spinner

Android Spinner for select country. Have country names for english and russian languages.
Also includes DefaultValueSpinner: implementation of Android Spinner with default value item for collapsed state

You can generate list of countries for other languages using `gen_country/gen_country.py` script.

`gen_country.py` depends on [pycountry library](https://pypi.python.org/pypi/pycountry)

Usage:
```
python ./gen_country.py <language_code>
```

Example:
```
python ./gen_country.py es # generates for spanish
```

If language code is not specified default for English is used

To install dependencies run `pip install -r gen_country/requirements.txt`

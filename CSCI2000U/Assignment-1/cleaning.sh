#!/bin/bash
# This is a comment
rm `find -name NOTES`
mkdir cleaned_data
mv ./data/*/* ./cleaned_data
for x in ./cleaned_data/*;do mv $x $x.txt;done;
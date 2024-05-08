This is the Andreu Sabater solution for pagination app task.
*Warning*
The app creates a directory on ./DOCS if it is not existing so please check the directory of execution to avoid overwrites

The application can be run by downloading the repository and via terminal calling:
  java -jar bin.jar.
I recommend this execution from the terminal on the downloaded directory to avoid overwrites.

When the aplication runs you just need to select the file you want to paginate and it will be done.

The input example is in the directory ./DOCS.
Generated outputs will be shown by a text area and also on a created file in ./DOCS/outputs.
The output file name is shown in the ui, but it will always be "paginated_" follwed by the original name of the file.
Code is in the folder ./src in both files, Main and Pagination.

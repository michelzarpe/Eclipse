<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <title>Test dojox.Grid Basic</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
        <style type="text/css">
        @import "http://o.aolcdn.com/dojo/1.0.0/dojox/grid/_grid/tundraGrid.css";
        @import "http://o.aolcdn.com/dojo/1.0.0/dijit/themes/tundra/tundra.css";
        @import "http://o.aolcdn.com/dojo/1.0.0/dojo/resources/dojo.css"
                body {
                        font-size: 0.9em;
                        font-family: Geneva, Arial, Helvetica, sans-serif;
                }
                .heading {
                        font-weight: bold;
                        padding-bottom: 0.25em;
                }
                               
                #grid {
                        border: 1px solid #333;
                        width: 35em;
                        height: 30em;
                }
        </style>
        <script type="text/javascript" src="http://o.aolcdn.com/dojo/1.0.0/dojo/dojo.xd.js"
                djConfig="isDebug:false, parseOnLoad: true"></script>
        <script type="text/javascript">
            dojo.require("dojo.data.ItemFileReadStore");
                dojo.require("dojox.grid.Grid");
                dojo.require("dojox.grid._data.model");
                dojo.require("dojo.parser");
       
                // a grid view is a group of columns. 
                var view1 = {
                        cells: [[
                                {name: 'Namespace', field: "namespace"},
                                {name: 'Class', width: "25em", field: "className"}
                          ],
                          [
                                {name: 'Summary', colSpan:"2", field: "summary"}
                          ]
                        ]
                };
                // a grid layout is an array of views.
                var layout = [ view1 ];
</script>
</head>
<body class="tundra">
<div class="heading">Our First Grid</div>
        <div dojoType="dojo.data.ItemFileReadStore"
                jsId="jsonStore" url="dijits.txt">
        </div>
        <div dojoType="dojox.grid.data.DojoData" jsId="model"
                rowsPerPage="20" store="jsonStore" query="{ namespace: '*' }">
        </div>
        <div id="grid" dojoType="dojox.Grid" model="model" structure="layout"></div>
</body>
</html>
<style>
    a {
    color: #008CDA;
    text-decoration: none;
}
#navcontainer {
    background: url("images/background.png") repeat-x scroll 0 0;
    height: 35px;
    position: relative;
    z-index: 99;
    text-align:center;
}
#navlist{
    margin-top:0px;
}
#navlist li {
    background: url("images/background.png") repeat-x scroll 0 -45px;
    display: inline-block;
    *zoom: 1;
    *display: inline;
    height: 35px;
    margin: 0;
    padding-left: 1px;
    text-align:center;
    vertical-align: middle;
}
#navlist li a {
    background: url("images/background.png") repeat-x scroll 0 -45px rgba(0, 0, 0, 0);
    display: inline-block;
    height: 35px;
    margin: 0;
    padding-left: 1px;
    text-align:center;
    vertical-align: middle;
    
}
#navlist li a img{
    text-align: center;
    
}
#navlist li a:link, #navlist li a:visited {
    background: url("images/background.png") repeat-x scroll 0 -90px rgba(0, 0, 0, 0);
    color: #FFFFFF;
    float: left;
    font-size: 14px;
    /*font-weight: bold;*/
    margin-right: 1px;
    text-shadow: 0 -1px 1px #000000;
}
#navlist .subnav li a:link, #navlist .subnav li a:visited {
    -moz-user-select: none;
    background-image: url("images/background.png");
    background-position: 0 0;
    background-repeat: repeat-x;
    color: #D6D6D6;
    display: block;
    text-align:left;
    font-family: "myriad-pro","myriad-pro-1","myriad-pro-2",sans-serif;
    /*font-weight: 600;*/
    height: 35px;
    line-height: 35px;
    margin: 0 1px;
    padding: 0 29px;
}
#navlist .subnav li a:link, #navlist .subnav li a:visited{
    background-position: 0 -180px;
    margin: 0;
    padding: 0 30px;
}
#navlist .subnav {
    background: none repeat scroll 0 0 #353535;
    border: 1px solid #424242;
    display: none;
    padding: 4px 0;
    position: absolute;
    top: 35px;
}
#navlist li:hover > .subnav{
    display: block;
    *display: inline;
    *list-style: none;
    *margin-left: -10%;
}
#navlist .subnav li {
    display: block;
    padding: 2px 0;
}
#logo {
    display: inline-block;
    height: 35px;
    margin: 0;
    padding-left: 1px;
    text-align:center;
    vertical-align: middle;
}

#userInfo {
    background: url("images/background.png") repeat-x scroll 0 -45px rgba(0, 0, 0, 0);
    display: inline-block;
    height: 35px;
    margin: 0;
    padding-left: 1px;
    text-align:center;
    vertical-align: middle;
}
</style>
<div id="navcontainer">
    <div id="logo" style="float:left;width:85px; ">
        <a style="color:white" href="./">Paternizer</a>
    </div>
    <div style="float:left;">
        <ul id="navlist">
            <li id="active"><a id="menu-Test" name="menu" href="#" style="width:150px">Template
                    <img src="images/dropdown.gif"/></a>
                <ul class="subnav" id="subnavlist">
                    <li id="subactive"><a name="menu" id="menuCreateTest" href="./index.jsp" style="width:400px">Generate File From an Existing Template</a></li>
                    <li id="subactive"><a name="menu" id="menuEditTest" href="./uploadTemplate.jsp" style="width:400px">Upload a New Template</a></li>
                </ul>
            </li>
            <li id="active"><a id="menu-TestCase" name="menu" href="#" style="width:150px">FTP
                    <img src="images/dropdown.gif"/></a>
                <ul class="subnav" id="subnavlist">
<!--                    <li id="subactive"><a name="menu" id="menuEditTestCase" href="./uploadOnFtp.jsp" style="width:300px">Send File by FTP</a></li>-->
                    <li id="subactive"><a name="menu" id="menuCreateTestCase" href="./getFromFTP.jsp" style="width:300px">Get From FTP</a></li>
                </ul>
            </li>
            <li id="active"><a id="menu-Data" name="menu" href="#" style="width:150px">JMS
                    <img src="images/dropdown.gif"/></a>
                <ul class="subnav" id="subnavlist">
                    <li id="subactive"><a name="menu" id="menuSqlLibrary" href="getFromJMS.jsp" style="width:300px">Read JMS Queue</a></li>
<!--                    <li id="subactive"><a name="menu" id="menuSoapLibrary" href="SoapLibrary.jsp" style="width:300px">Send message in JMS Queue</a></li>-->
                </ul>
            </li>
        </ul>
    </div>
</div>
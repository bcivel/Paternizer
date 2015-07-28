/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadJSON(path, data, success, error)
{
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function()
    {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                if (success)
                    success(JSON.parse(xhr.responseText));
            } else {
                if (error)
                    error(xhr);
            }
        }
    };
    xhr.open("GET", path + "?" + data, true);
    xhr.send();
}

function showDoc() {
    window.open('images/consigne.jpg', 'popup',
            'width=600,height=500,scrollbars=yes,menubar=false,location=false');
}

function recordCookie(id, cookieName) {
    var expiration_date = new Date();
    expiration_date.setFullYear(expiration_date.getFullYear() + 1);

    var val = $("#"+id).val();
    document.cookie = cookieName+"=" + val + ";expires=" + expiration_date.toGMTString();
}

function setCookie(cookieName, element) {
    var name = cookieName + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        var val = c.split('=')[1];
        if (c.indexOf(name) === 0) {
            document.getElementById(element).value = val;
        }
    }
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) === 0)
            return c.substring(name.length, c.length);
    }
    return "";
}


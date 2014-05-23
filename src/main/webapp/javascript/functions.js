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
    xhr.open("GET", path + "?" +data, true);
    xhr.send();
}

function showDoc(){
    window.open('images/consigne.jpg', 'popup',
    'width=600,height=500,scrollbars=yes,menubar=false,location=false');    
}

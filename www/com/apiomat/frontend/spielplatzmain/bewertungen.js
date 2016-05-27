/*
 * Copyright (c) 2011 - 2015, Apinauten GmbH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * THIS FILE IS GENERATED AUTOMATICALLY. DON'T MODIFY IT.
 */
 
/* define namespace */

if(typeof goog !== 'undefined')
{
    goog.provide('Apiomat.bewertungen');
    goog.require('Apiomat.AbstractClientDataModel');
}
if(typeof exports === 'undefined')
{
    var Apiomat = Apiomat || {};
}
(function(Apiomat)
{
Apiomat.bewertungen = function() {
    this.init();
    /* referenced object methods */

};
/* static methods */

/**
 * Callback required by getbewertungens function.
 * callback name getbewertungensCallback
 * @param {function} onOk Function is called when everything is ok. (containing a list of object as {array})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns a list of objects of this class from server.
 *
 * If query is given than returend list will be filtered by the given query
 *
 * @param {string} query (optional) a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {getbewertungensCallback} callback
 */
Apiomat.bewertungen.getbewertungens = function(query, callback) {
    Apiomat.Datastore.getInstance().loadListFromServerWithClass(Apiomat.bewertungen, query, callback,false);
};

/**
 * Callback required by getbewertungensAndRefHref function.
 * callback name getbewertungensAndRefHrefCallback
 * @param {function} onOk Function is called when everything is ok. (containing a list of object as {array})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns a list of objects of this class from server.
 *
 * If query is given than returend list will be filtered by the given query
 *
 * @param {string} query (optional) a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {boolean} withReferencedHrefs set to true to get also all HREFs of referenced class instances
 * @param {getbewertungensAndRefHrefCallback} callback which is called when request finished
 */
Apiomat.bewertungen.getbewertungensAndRefHref = function(query, callback,withReferencedHrefs) {
    Apiomat.Datastore.getInstance().loadListFromServerWithClass(Apiomat.bewertungen, query, callback, withReferencedHrefs);
};

/**
 * Callback required by getbewertungensCount function.
 * callback name getbewertungensCountCallback
 * @param {function} onOk Function is called when everything is ok. (containing count as {number})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns count of objects of this class filtered by the given query from server
 * 
 * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {getbewertungensCountCallback} callback which is called when request finished
 */
Apiomat.bewertungen.getbewertungensCount = function(query, callback) {
    Apiomat.Datastore.getInstance().loadCountFromServer(Apiomat.bewertungen, undefined, query, callback);
}


/* inheritance */
Apiomat.bewertungen.prototype = new Apiomat.AbstractClientDataModel();
Apiomat.bewertungen.prototype.constructor = Apiomat.bewertungen;

Apiomat.bewertungen.prototype.init=function () {
        this.data = new Object();
}
/**
 * get simple name
 * @return {string} className
 */
Apiomat.bewertungen.prototype.getSimpleName = function() {
    return "bewertungen";
};
/**
 * get module name
 * @return {string} moduleName
 */
Apiomat.bewertungen.prototype.getModuleName = function() {
    return "SpielplatzMain";
};

/* easy getter and setter */

        
/**
 * get Bewertungsid
 * @return Bewertungsid
 */
Apiomat.bewertungen.prototype.getBewertungsid = function() 
{
    return this.data.bewertungsid;
};

/**
 * set Bewertungsid
 * @param Bewertungsid
 */
Apiomat.bewertungen.prototype.setBewertungsid = function(_bewertungsid) {
    this.data.bewertungsid = _bewertungsid;
};

    /**
 * Returns an URL of the image.
 
 <br/> You can provide several optional parameters to
 * manipulate the image:
 * 
 * @param width (optional)
 *            the width of the image, 0 to use the original size. If only width
 *            or height are provided, the other value is computed.
 * @param height (optional)
 *            the height of the image, 0 to use the original size. If only width
 *            or height are provided, the other value is computed.
 * @param backgroundColorAsHex (optional)
 *            the background color of the image, null or empty uses the original
 *            background color. Caution: Don't send the '#' symbol! Example:
 *            <i>ff0000</i>
 * @param alpha (optional)
 *            the alpha value of the image (between 0 and 1), null to take the original value.
 * @param format (optional)
 *            the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
  * @return the URL of the image
 */
Apiomat.bewertungen.prototype.getBildURL = function(width, height, bgColorAsHex, alpha, format) 
{
    var url = this.data.bildURL;
    if(!url)
    {
        return undefined;
    }
    url += ".img?apiKey=" + Apiomat.User.AOMAPIKEY + "&system=" + Apiomat.User.AOMSYS;
    if (width) {
        url += "&width=" + width;
    }
    if (height) {
        url += "&height=" + height;
    }
    if (bgColorAsHex) {
        url += "&bgcolor=" + bgColorAsHex;
    }
    if (alpha) {
        url += "&alpha=" + alpha;
    }
    if (format) {
        url += "&format=" + format;
    }
    return url;
}

/**
 * Callback required by loadBild function.
 * callback name loadBildCountCallback
  * @param {function} onOk Function is called when everything is ok. (containg image as bytearray)
  * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/** 
 * Load referenced object(s) and
 * add result from server to member variable of this class. <br/> You can provide several optional parameters to
 * manipulate the image:
 * 
 * @param width (optional)
 *            the width of the image, 0 to use the original size. If only width
 *            or height are provided, the other value is computed.
 * @param height (optional)
 *            the height of the image, 0 to use the original size. If only width
 *            or height are provided, the other value is computed.
 * @param bgColorAsHex (optional)
 *            the background color of the image, null or empty uses the original
 *            background color. Caution: Don't send the '#' symbol! Example:
 *            <i>ff0000</i>
 * @param alpha (optional)
 *            the alpha value of the image (between 0 and 1), null to take the original value.
 * @param format (optional)
 *            the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
 * @param {loadBildCountCallback} _callback
  * @return the ressource URL of the image
 */
Apiomat.bewertungen.prototype.loadBild = function(width, height, bgColorAsHex, alpha, format,_callback)
{
    var resUrl = this.getBildURL(width, height, bgColorAsHex, alpha, format);
    if (typeof resUrl == "undefined")
    {
        throw new Apiomat.ApiomatRequestError(
                        Apiomat.Status.ATTACHED_HREF_MISSING,200);
    }    
    return Apiomat.Datastore.getInstance().loadResource(resUrl, _callback);
}

/**
 * Callback required by postBild functions.
 * callback name postBildCallback
 * @param {function} onOk Function is called when everything is ok.
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * add a image
 * @param _data imagedata as bytearray
 * @param {postBildCallback} _callback
 */
Apiomat.bewertungen.prototype.postBild = function(_data, _callback) 
{
    var postCB = {
            onOk : function(_imgHref) {
                if (_imgHref) {
                    this.parent.data.bildURL = _imgHref;
                    /* update object again */
                    this.parent.save({
                        onOk : function() {
                            Apiomat.Datastore.positiveCallback(_callback);
                        },
                        onError : function(error) {
                            var deleteCB = {
                                onOk : function() {
                                    Apiomat.Datastore.negativeCallback(_callback,error);
                                },
                                onError : function(e) {
                                    Apiomat.Datastore.negativeCallback(_callback,error);
                                }
                            };
                            Apiomat.Datastore.getInstance().deleteOnServer(_imgHref, deleteCB);
                            delete this.parent.data.bildURL;
                        }
                    });
                }
                else {
                    var error = new Apiomat.ApiomatRequestError(Apiomat.Status.HREF_NOT_FOUND);
                    if (_callback) {
                        Apiomat.Datastore.negativeCallback(_callback,error);
                    } else if(console && console.log) {
                        console.log("Error occured: " + error);
                    }
                }
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(_callback,error);
            }
    };
    postCB.parent = this;
    if(Apiomat.Datastore.getInstance().shouldSendOffline("POST"))
    {
        Apiomat.Datastore.getInstance( ).sendOffline( "POST", null, _data, true, postCB );
    }
    else
    {
        Apiomat.Datastore.getInstance().postStaticDataOnServer(_data, true, postCB);
    }
};

/**
 * Callback required by deleteBild functions.
 * callback name deleteBildCallback
 * @param {function} onOk Function is called when everything is ok.
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * delete a image
 * @param {deleteBildCallback} _callback
 */
Apiomat.bewertungen.prototype.deleteBild = function(_callback) 
{
    var imageHref = this.data.bildURL;
	// First try to delete the attribute and then save, to find out if the caller is allowed to do so
    delete this.data.bildURL;
    /* update object again and save deleted image reference in object */
    var saveCB= {
        onOk : function() {
            //save was successful, now call delete on server
            var deleteCB = {
                onOk : function() {
                    Apiomat.Datastore.positiveCallback(_callback);
                },
                onError : function(error) {
                    Apiomat.Datastore.negativeCallback(_callback,error);
                }
             };
             if(Apiomat.Datastore.getInstance().shouldSendOffline("DELETE"))
             {
                 Apiomat.Datastore.getInstance( ).sendOffline( "DELETE", imageHref, null, null, deleteCB );
             }
             else
             {
                 Apiomat.Datastore.getInstance().deleteOnServer(imageHref, deleteCB);
             }
        },
        onError : function(error) {
            //save was unsuccessful, reset data
            this.parent.data.bildURL=imageHref;
        
           Apiomat.Datastore.negativeCallback(_callback,error);
           }
    };
	saveCB.parent=this;
    this.save(saveCB);
};

        
/**
 * get Gesamtbewertung
 * @return Gesamtbewertung
 */
Apiomat.bewertungen.prototype.getGesamtbewertung = function() 
{
    return this.data.gesamtbewertung;
};

/**
 * set Gesamtbewertung
 * @param Gesamtbewertung
 */
Apiomat.bewertungen.prototype.setGesamtbewertung = function(_gesamtbewertung) {
    this.data.gesamtbewertung = _gesamtbewertung;
};

        
/**
 * get Nickname
 * @return Nickname
 */
Apiomat.bewertungen.prototype.getNickname = function() 
{
    return this.data.nickname;
};

/**
 * set Nickname
 * @param Nickname
 */
Apiomat.bewertungen.prototype.setNickname = function(_nickname) {
    this.data.nickname = _nickname;
};

        
/**
 * get Sauberkeit
 * @return Sauberkeit
 */
Apiomat.bewertungen.prototype.getSauberkeit = function() 
{
    return this.data.sauberkeit;
};

/**
 * set Sauberkeit
 * @param Sauberkeit
 */
Apiomat.bewertungen.prototype.setSauberkeit = function(_sauberkeit) {
    this.data.sauberkeit = _sauberkeit;
};

        
/**
 * get Sicherheit
 * @return Sicherheit
 */
Apiomat.bewertungen.prototype.getSicherheit = function() 
{
    return this.data.sicherheit;
};

/**
 * set Sicherheit
 * @param Sicherheit
 */
Apiomat.bewertungen.prototype.setSicherheit = function(_sicherheit) {
    this.data.sicherheit = _sicherheit;
};

        
/**
 * get Spielspass
 * @return Spielspass
 */
Apiomat.bewertungen.prototype.getSpielspass = function() 
{
    return this.data.spielspass;
};

/**
 * set Spielspass
 * @param Spielspass
 */
Apiomat.bewertungen.prototype.setSpielspass = function(_spielspass) {
    this.data.spielspass = _spielspass;
};

        
/**
 * get Textbewertung
 * @return Textbewertung
 */
Apiomat.bewertungen.prototype.getTextbewertung = function() 
{
    return this.data.textbewertung;
};

/**
 * set Textbewertung
 * @param Textbewertung
 */
Apiomat.bewertungen.prototype.setTextbewertung = function(_textbewertung) {
    this.data.textbewertung = _textbewertung;
};

        
/**
 * get Ueberschriftbewertungskommentar
 * @return Ueberschriftbewertungskommentar
 */
Apiomat.bewertungen.prototype.getUeberschriftbewertungskommentar = function() 
{
    return this.data.ueberschriftbewertungskommentar;
};

/**
 * set Ueberschriftbewertungskommentar
 * @param Ueberschriftbewertungskommentar
 */
Apiomat.bewertungen.prototype.setUeberschriftbewertungskommentar = function(_ueberschriftbewertungskommentar) {
    this.data.ueberschriftbewertungskommentar = _ueberschriftbewertungskommentar;
};


})(typeof exports === 'undefined' ? Apiomat
        : exports);

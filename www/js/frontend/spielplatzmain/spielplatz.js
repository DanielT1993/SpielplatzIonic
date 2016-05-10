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
    goog.provide('Apiomat.spielplatz');
    goog.require('Apiomat.AbstractClientDataModel');
}
if(typeof exports === 'undefined')
{
    var Apiomat = Apiomat || {};
}
(function(Apiomat)
{
Apiomat.spielplatz = function() {
    this.init();
    /* referenced object methods */
    
    var ausstattungsliste = [];
    
    /**
     * Getter for local linked variable
     * @return {string} attributeName 
     */
    this.getAusstattungsliste = function() 
    {
        return this.ausstattungsliste;
    };
	
    /**
     * Callback required by loadAusstattungsliste function.
     * callback name loadAusstattungslisteCallback
	      * @param {function} onOk Function is called when everything is ok. (containing referenced objects as {array})
	      * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
    
    /** 
     * Load referenced object(s) and
     * add result from server to member variable of this class.
 * @param a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)	 * @param {loadAusstattungslisteCallback} callback
	 */
    this.loadAusstattungsliste = function(query,callback) 
    {
        var refUrl = this.data.ausstattungslisteHref;
        if (typeof refUrl == "undefined")
        {
            throw new Apiomat.ApiomatRequestError(Apiomat.Status.ATTACHED_HREF_MISSING,200);
        }    
        var loadFromServerCB={
            onOk : function(obj) {
                this.parent.ausstattungsliste = obj;
                Apiomat.Datastore.positiveCallback(callback,obj);
            },
            onError : function(error) {
                if (error.statusCode==204) {
                    this.parent.ausstattungsliste = null;
                }
                Apiomat.Datastore.negativeCallback(callback,error);
            }
        };
        loadFromServerCB.parent=this;
        Apiomat.Datastore.getInstance().loadFromServer(refUrl,loadFromServerCB, undefined,false, query, Apiomat.ausstattung);
    };
	
    /**
     * Callback required by loadAusstattungslisteAndRefHref function.
     * callback name loadAusstattungslisteAndRefHrefCallback
          * @param {function} onOk Function is called when everything is ok. (containing referenced objects with refHref as {array})
     	 * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
    
    /** 
     * Load referenced object(s) with refHref and
     * add result from server to member variable of this class.
 * @param a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)	 * @param {loadAusstattungslisteAndRefHrefCallback} callback
     */
    this.loadAusstattungslisteAndRefHref = function(query,callback) 
    {
        var refUrl = this.data.ausstattungslisteHref;
        if (typeof refUrl == "undefined")
        {
            throw new Apiomat.ApiomatRequestError(Apiomat.Status.ATTACHED_HREF_MISSING,200);
        }
        
        var loadFromServerCB={
            onOk : function(obj) {
                this.parent.ausstattungsliste = obj;
                Apiomat.Datastore.positiveCallback(callback,obj);
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(callback,error);
            }
        }
        loadFromServerCB.parent=this;
        Apiomat.Datastore.getInstance().loadFromServer(refUrl,loadFromServerCB , undefined,true, query, Apiomat.ausstattung);
    };
    
    /**
     * Callback required by postAusstattungsliste function.
     * callback name postAusstattungslisteCallback
     * @param {function} onOk Function is called when everything is ok. (containing refHref as {string})
     * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
	
    /**
     * Adds a given reference to this object
	 * @param _refData reference
	 * @param {postAusstattungslisteCallback} _callback
     */
    this.postAusstattungsliste = function(_refData, _callback) 
    {
        if(_refData == false || typeof _refData.getHref() === 'undefined') {
            var error = new Apiomat.ApiomatRequestError(Apiomat.Status.SAVE_REFERENECE_BEFORE_REFERENCING);
            if (_callback) {
                    Apiomat.Datastore.negativeCallback(_callback,error);
            } else if(console && console.log) {
                    console.log("Error occured: " + error);
            }
            return;
        }
        
        var callback = {
            onOk : function(refHref) {
                if (refHref) {
                                    /* only add reference data if not already in local list */
                    if( this.parent.ausstattungsliste.filter(function(_elem) {
                        return _elem.getHref() && refHref && _elem.getHref() === refHref;
                    }).length < 1)
                    {
                        this.parent.ausstattungsliste.push(_refData);
                    } 
                                }
                Apiomat.Datastore.positiveCallback(_callback,refHref);
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(_callback,error);
            }
        };
        callback.parent=this;
        if(Apiomat.Datastore.getInstance().shouldSendOffline("POST"))
        {
            Apiomat.Datastore.getInstance( ).sendOffline( "POST", this.getHref(), _refData, "ausstattungsliste", callback );
        }
        else
        {
            Apiomat.Datastore.getInstance().postOnServer(_refData, callback, this.data.ausstattungslisteHref);
        }
    };
	
    /**
     * Callback required by removeAusstattungsliste function.
     * callback name removeAusstattungslisteCallback
     * @param {function} onOk Function is called when everything is ok.
     * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
    
    /**
     * remove a given reference to this object
	 * @param _refData reference
	 * @param {removeAusstattungslisteCallback} _callback
     */
    this.removeAusstattungsliste = function(_refData, _callback) 
    {
        var id = _refData.getHref().substring(_refData.getHref().lastIndexOf("/") + 1);
        var deleteHref = this.data.ausstattungslisteHref + "/" + id;
        var callback = {
            onOk : function(obj) {
                            /* Find and remove reference from local list */
                var i=-1;
                if(typeof this.parent.ausstattungsliste !="undefined" && typeof this.parent.ausstattungsliste.length!="undefined" && this.parent.ausstattungsliste.length>0) {
                    for (var k=0;k<this.parent.ausstattungsliste.length;k++) {
                        if (this.parent.ausstattungsliste[k].data.href===_refData.data.href) {
                            i=k;
                            break;
                        }
                    }
                }
                
                if(i != -1) {
                    this.parent.ausstattungsliste.splice(i, 1);
                }
            ;                 
                Apiomat.Datastore.positiveCallback(_callback);
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(_callback,error);
            }
        };
        callback.parent=this;
        Apiomat.Datastore.getInstance().deleteOnServer(deleteHref, callback);
    };
    
        /**
     * Callback required by getAusstattungslisteCount function.
     * callback name getAusstattungslisteCountCallback
     * @param {function} onOk Function is called when everything is ok. (containg count as {number})
     * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
	
    /**
     * Returns a count of referenced objects of this class filtered by the given query from server
     * @param a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param {getAusstattungslisteCountCallback} _callback
	 */
    this.getAusstattungslisteCount = function(_query, _callback) 
    {
        Apiomat.Datastore.getInstance().loadCountFromServer(this.getHref(), 'ausstattungsliste', _query, _callback);
    };
        
    var bewertungsliste = [];
    
    /**
     * Getter for local linked variable
     * @return {string} attributeName 
     */
    this.getBewertungsliste = function() 
    {
        return this.bewertungsliste;
    };
	
    /**
     * Callback required by loadBewertungsliste function.
     * callback name loadBewertungslisteCallback
	      * @param {function} onOk Function is called when everything is ok. (containing referenced objects as {array})
	      * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
    
    /** 
     * Load referenced object(s) and
     * add result from server to member variable of this class.
 * @param a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)	 * @param {loadBewertungslisteCallback} callback
	 */
    this.loadBewertungsliste = function(query,callback) 
    {
        var refUrl = this.data.bewertungslisteHref;
        if (typeof refUrl == "undefined")
        {
            throw new Apiomat.ApiomatRequestError(Apiomat.Status.ATTACHED_HREF_MISSING,200);
        }    
        var loadFromServerCB={
            onOk : function(obj) {
                this.parent.bewertungsliste = obj;
                Apiomat.Datastore.positiveCallback(callback,obj);
            },
            onError : function(error) {
                if (error.statusCode==204) {
                    this.parent.bewertungsliste = null;
                }
                Apiomat.Datastore.negativeCallback(callback,error);
            }
        };
        loadFromServerCB.parent=this;
        Apiomat.Datastore.getInstance().loadFromServer(refUrl,loadFromServerCB, undefined,false, query, Apiomat.bewertungen);
    };
	
    /**
     * Callback required by loadBewertungslisteAndRefHref function.
     * callback name loadBewertungslisteAndRefHrefCallback
          * @param {function} onOk Function is called when everything is ok. (containing referenced objects with refHref as {array})
     	 * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
    
    /** 
     * Load referenced object(s) with refHref and
     * add result from server to member variable of this class.
 * @param a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)	 * @param {loadBewertungslisteAndRefHrefCallback} callback
     */
    this.loadBewertungslisteAndRefHref = function(query,callback) 
    {
        var refUrl = this.data.bewertungslisteHref;
        if (typeof refUrl == "undefined")
        {
            throw new Apiomat.ApiomatRequestError(Apiomat.Status.ATTACHED_HREF_MISSING,200);
        }
        
        var loadFromServerCB={
            onOk : function(obj) {
                this.parent.bewertungsliste = obj;
                Apiomat.Datastore.positiveCallback(callback,obj);
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(callback,error);
            }
        }
        loadFromServerCB.parent=this;
        Apiomat.Datastore.getInstance().loadFromServer(refUrl,loadFromServerCB , undefined,true, query, Apiomat.bewertungen);
    };
    
    /**
     * Callback required by postBewertungsliste function.
     * callback name postBewertungslisteCallback
     * @param {function} onOk Function is called when everything is ok. (containing refHref as {string})
     * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
	
    /**
     * Adds a given reference to this object
	 * @param _refData reference
	 * @param {postBewertungslisteCallback} _callback
     */
    this.postBewertungsliste = function(_refData, _callback) 
    {
        if(_refData == false || typeof _refData.getHref() === 'undefined') {
            var error = new Apiomat.ApiomatRequestError(Apiomat.Status.SAVE_REFERENECE_BEFORE_REFERENCING);
            if (_callback) {
                    Apiomat.Datastore.negativeCallback(_callback,error);
            } else if(console && console.log) {
                    console.log("Error occured: " + error);
            }
            return;
        }
        
        var callback = {
            onOk : function(refHref) {
                if (refHref) {
                                    /* only add reference data if not already in local list */
                    if( this.parent.bewertungsliste.filter(function(_elem) {
                        return _elem.getHref() && refHref && _elem.getHref() === refHref;
                    }).length < 1)
                    {
                        this.parent.bewertungsliste.push(_refData);
                    } 
                                }
                Apiomat.Datastore.positiveCallback(_callback,refHref);
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(_callback,error);
            }
        };
        callback.parent=this;
        if(Apiomat.Datastore.getInstance().shouldSendOffline("POST"))
        {
            Apiomat.Datastore.getInstance( ).sendOffline( "POST", this.getHref(), _refData, "bewertungsliste", callback );
        }
        else
        {
            Apiomat.Datastore.getInstance().postOnServer(_refData, callback, this.data.bewertungslisteHref);
        }
    };
	
    /**
     * Callback required by removeBewertungsliste function.
     * callback name removeBewertungslisteCallback
     * @param {function} onOk Function is called when everything is ok.
     * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
    
    /**
     * remove a given reference to this object
	 * @param _refData reference
	 * @param {removeBewertungslisteCallback} _callback
     */
    this.removeBewertungsliste = function(_refData, _callback) 
    {
        var id = _refData.getHref().substring(_refData.getHref().lastIndexOf("/") + 1);
        var deleteHref = this.data.bewertungslisteHref + "/" + id;
        var callback = {
            onOk : function(obj) {
                            /* Find and remove reference from local list */
                var i=-1;
                if(typeof this.parent.bewertungsliste !="undefined" && typeof this.parent.bewertungsliste.length!="undefined" && this.parent.bewertungsliste.length>0) {
                    for (var k=0;k<this.parent.bewertungsliste.length;k++) {
                        if (this.parent.bewertungsliste[k].data.href===_refData.data.href) {
                            i=k;
                            break;
                        }
                    }
                }
                
                if(i != -1) {
                    this.parent.bewertungsliste.splice(i, 1);
                }
            ;                 
                Apiomat.Datastore.positiveCallback(_callback);
            },
            onError : function(error) {
                Apiomat.Datastore.negativeCallback(_callback,error);
            }
        };
        callback.parent=this;
        Apiomat.Datastore.getInstance().deleteOnServer(deleteHref, callback);
    };
    
        /**
     * Callback required by getBewertungslisteCount function.
     * callback name getBewertungslisteCountCallback
     * @param {function} onOk Function is called when everything is ok. (containg count as {number})
     * @param {function} onError Function is called when an error occurs. (containing the error object) 
     */
	
    /**
     * Returns a count of referenced objects of this class filtered by the given query from server
     * @param a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param {getBewertungslisteCountCallback} _callback
	 */
    this.getBewertungslisteCount = function(_query, _callback) 
    {
        Apiomat.Datastore.getInstance().loadCountFromServer(this.getHref(), 'bewertungsliste', _query, _callback);
    };
    
};
/* static methods */

/**
 * Callback required by getspielplatzs function.
 * callback name getspielplatzsCallback
 * @param {function} onOk Function is called when everything is ok. (containing a list of object as {array})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns a list of objects of this class from server.
 *
 * If query is given than returend list will be filtered by the given query
 *
 * @param {string} query (optional) a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {getspielplatzsCallback} callback
 */
Apiomat.spielplatz.getspielplatzs = function(query, callback) {
    Apiomat.Datastore.getInstance().loadListFromServerWithClass(Apiomat.spielplatz, query, callback,false);
};

/**
 * Callback required by getspielplatzsAndRefHref function.
 * callback name getspielplatzsAndRefHrefCallback
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
 * @param {getspielplatzsAndRefHrefCallback} callback which is called when request finished
 */
Apiomat.spielplatz.getspielplatzsAndRefHref = function(query, callback,withReferencedHrefs) {
    Apiomat.Datastore.getInstance().loadListFromServerWithClass(Apiomat.spielplatz, query, callback, withReferencedHrefs);
};

/**
 * Callback required by getspielplatzsCount function.
 * callback name getspielplatzsCountCallback
 * @param {function} onOk Function is called when everything is ok. (containing count as {number})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns count of objects of this class filtered by the given query from server
 * 
 * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {getspielplatzsCountCallback} callback which is called when request finished
 */
Apiomat.spielplatz.getspielplatzsCount = function(query, callback) {
    Apiomat.Datastore.getInstance().loadCountFromServer(Apiomat.spielplatz, undefined, query, callback);
}


/* inheritance */
Apiomat.spielplatz.prototype = new Apiomat.AbstractClientDataModel();
Apiomat.spielplatz.prototype.constructor = Apiomat.spielplatz;

Apiomat.spielplatz.prototype.init=function () {
        this.data = new Object();
    this.ausstattungsliste = [];
    this.bewertungsliste = [];
}
/**
 * get simple name
 * @return {string} className
 */
Apiomat.spielplatz.prototype.getSimpleName = function() {
    return "spielplatz";
};
/**
 * get module name
 * @return {string} moduleName
 */
Apiomat.spielplatz.prototype.getModuleName = function() {
    return "SpielplatzMain";
};

/* easy getter and setter */

        
/**
 * get Altersgruppe
 * @return Altersgruppe
 */
Apiomat.spielplatz.prototype.getAltersgruppe = function() 
{
    return this.data.altersgruppe;
};

/**
 * set Altersgruppe
 * @param Altersgruppe
 */
Apiomat.spielplatz.prototype.setAltersgruppe = function(_altersgruppe) {
    this.data.altersgruppe = _altersgruppe;
};

        
/**
 * get Ausstattungsliste
 * @return Ausstattungsliste
 */
Apiomat.spielplatz.prototype.getAusstattungsliste = function() 
{
    return this.data.ausstattungsliste;
};

/**
 * set Ausstattungsliste
 * @param Ausstattungsliste
 */
Apiomat.spielplatz.prototype.setAusstattungsliste = function(_ausstattungsliste) {
    this.data.ausstattungsliste = _ausstattungsliste;
};

        
/**
 * get Bewertungsliste
 * @return Bewertungsliste
 */
Apiomat.spielplatz.prototype.getBewertungsliste = function() 
{
    return this.data.bewertungsliste;
};

/**
 * set Bewertungsliste
 * @param Bewertungsliste
 */
Apiomat.spielplatz.prototype.setBewertungsliste = function(_bewertungsliste) {
    this.data.bewertungsliste = _bewertungsliste;
};

        
/**
 * get Größe
 * @return Größe
 */
Apiomat.spielplatz.prototype.getGröße = function() 
{
    return this.data.größe;
};

/**
 * set Größe
 * @param Größe
 */
Apiomat.spielplatz.prototype.setGröße = function(_größe) {
    this.data.größe = _größe;
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
Apiomat.spielplatz.prototype.getHauptbildURL = function(width, height, bgColorAsHex, alpha, format) 
{
    var url = this.data.hauptbildURL;
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
 * Callback required by loadHauptbild function.
 * callback name loadHauptbildCountCallback
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
 * @param {loadHauptbildCountCallback} _callback
  * @return the ressource URL of the image
 */
Apiomat.spielplatz.prototype.loadHauptbild = function(width, height, bgColorAsHex, alpha, format,_callback)
{
    var resUrl = this.getHauptbildURL(width, height, bgColorAsHex, alpha, format);
    if (typeof resUrl == "undefined")
    {
        throw new Apiomat.ApiomatRequestError(
                        Apiomat.Status.ATTACHED_HREF_MISSING,200);
    }    
    return Apiomat.Datastore.getInstance().loadResource(resUrl, _callback);
}

/**
 * Callback required by postHauptbild functions.
 * callback name postHauptbildCallback
 * @param {function} onOk Function is called when everything is ok.
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * add a image
 * @param _data imagedata as bytearray
 * @param {postHauptbildCallback} _callback
 */
Apiomat.spielplatz.prototype.postHauptbild = function(_data, _callback) 
{
    var postCB = {
            onOk : function(_imgHref) {
                if (_imgHref) {
                    this.parent.data.hauptbildURL = _imgHref;
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
                            delete this.parent.data.hauptbildURL;
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
 * Callback required by deleteHauptbild functions.
 * callback name deleteHauptbildCallback
 * @param {function} onOk Function is called when everything is ok.
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * delete a image
 * @param {deleteHauptbildCallback} _callback
 */
Apiomat.spielplatz.prototype.deleteHauptbild = function(_callback) 
{
    var imageHref = this.data.hauptbildURL;
	// First try to delete the attribute and then save, to find out if the caller is allowed to do so
    delete this.data.hauptbildURL;
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
            this.parent.data.hauptbildURL=imageHref;
        
           Apiomat.Datastore.negativeCallback(_callback,error);
           }
    };
	saveCB.parent=this;
    this.save(saveCB);
};

        
/**
 * get Hausnummer
 * @return Hausnummer
 */
Apiomat.spielplatz.prototype.getHausnummer = function() 
{
    return this.data.hausnummer;
};

/**
 * set Hausnummer
 * @param Hausnummer
 */
Apiomat.spielplatz.prototype.setHausnummer = function(_hausnummer) {
    this.data.hausnummer = _hausnummer;
};

   
/**
 * get Location latitude
 * @return latitude as {floating number}
 */
Apiomat.spielplatz.prototype.getLocationLatitude = function() 
{
    var locArr = this.data.location;
    if(locArr)
    {
        return locArr[0];
    }
};

/**
 * get Location longitude
 * @return longitude as {floating number}
 */
Apiomat.spielplatz.prototype.getLocationLongitude = function() 
{
    var locArr = this.data.location;
    if(locArr)
    {
        return locArr[1];
    }
};

/**
 * set latitude
 * @param latitude
 */
Apiomat.spielplatz.prototype.setLocationLatitude = function(_latitude) 
{
    var locArr = this.data.location;
    if(!locArr)
    {
        locArr = [_latitude, undefined];
    }
    else
    {
        locArr[0] = _latitude;
    }
    this.data.location = locArr;
};

/**
 * set longitude
 * @param longitude
 */
Apiomat.spielplatz.prototype.setLocationLongitude = function(_longitude) 
{
    var locArr = this.data.location;
    if(!locArr)
    {
        locArr = [0, _longitude];
    }
    else
    {
        locArr[1] = _longitude;
    }
    this.data.location = locArr;
};

        
/**
 * get Name
 * @return Name
 */
Apiomat.spielplatz.prototype.getName = function() 
{
    return this.data.name;
};

/**
 * set Name
 * @param Name
 */
Apiomat.spielplatz.prototype.setName = function(_name) {
    this.data.name = _name;
};

        
/**
 * get Sitzgelegenheiten
 * @return Sitzgelegenheiten
 */
Apiomat.spielplatz.prototype.getSitzgelegenheiten = function() 
{
    return this.data.sitzgelegenheiten;
};

/**
 * set Sitzgelegenheiten
 * @param Sitzgelegenheiten
 */
Apiomat.spielplatz.prototype.setSitzgelegenheiten = function(_sitzgelegenheiten) {
    this.data.sitzgelegenheiten = _sitzgelegenheiten;
};

        
/**
 * get Spielplatzid
 * @return Spielplatzid
 */
Apiomat.spielplatz.prototype.getSpielplatzid = function() 
{
    return this.data.spielplatzid;
};

/**
 * set Spielplatzid
 * @param Spielplatzid
 */
Apiomat.spielplatz.prototype.setSpielplatzid = function(_spielplatzid) {
    this.data.spielplatzid = _spielplatzid;
};

        
/**
 * get Stadtteil
 * @return Stadtteil
 */
Apiomat.spielplatz.prototype.getStadtteil = function() 
{
    return this.data.stadtteil;
};

/**
 * set Stadtteil
 * @param Stadtteil
 */
Apiomat.spielplatz.prototype.setStadtteil = function(_stadtteil) {
    this.data.stadtteil = _stadtteil;
};

        
/**
 * get Status
 * @return Status
 */
Apiomat.spielplatz.prototype.getStatus = function() 
{
    return this.data.status;
};

/**
 * set Status
 * @param Status
 */
Apiomat.spielplatz.prototype.setStatus = function(_status) {
    this.data.status = _status;
};

        
/**
 * get Straße
 * @return Straße
 */
Apiomat.spielplatz.prototype.getStraße = function() 
{
    return this.data.straße;
};

/**
 * set Straße
 * @param Straße
 */
Apiomat.spielplatz.prototype.setStraße = function(_straße) {
    this.data.straße = _straße;
};


})(typeof exports === 'undefined' ? Apiomat
        : exports);

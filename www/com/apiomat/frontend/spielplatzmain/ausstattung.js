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
    goog.provide('Apiomat.ausstattung');
    goog.require('Apiomat.AbstractClientDataModel');
}
if(typeof exports === 'undefined')
{
    var Apiomat = Apiomat || {};
}
(function(Apiomat)
{
Apiomat.ausstattung = function() {
    this.init();
    /* referenced object methods */

};
/* static methods */

/**
 * Callback required by getausstattungs function.
 * callback name getausstattungsCallback
 * @param {function} onOk Function is called when everything is ok. (containing a list of object as {array})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns a list of objects of this class from server.
 *
 * If query is given than returend list will be filtered by the given query
 *
 * @param {string} query (optional) a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {getausstattungsCallback} callback
 */
Apiomat.ausstattung.getausstattungs = function(query, callback) {
    Apiomat.Datastore.getInstance().loadListFromServerWithClass(Apiomat.ausstattung, query, callback,false);
};

/**
 * Callback required by getausstattungsAndRefHref function.
 * callback name getausstattungsAndRefHrefCallback
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
 * @param {getausstattungsAndRefHrefCallback} callback which is called when request finished
 */
Apiomat.ausstattung.getausstattungsAndRefHref = function(query, callback,withReferencedHrefs) {
    Apiomat.Datastore.getInstance().loadListFromServerWithClass(Apiomat.ausstattung, query, callback, withReferencedHrefs);
};

/**
 * Callback required by getausstattungsCount function.
 * callback name getausstattungsCountCallback
 * @param {function} onOk Function is called when everything is ok. (containing count as {number})
 * @param {function} onError Function is called when an error occurs. (containing the error object) 
 */

/**
 * Returns count of objects of this class filtered by the given query from server
 * 
 * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
 * @param {getausstattungsCountCallback} callback which is called when request finished
 */
Apiomat.ausstattung.getausstattungsCount = function(query, callback) {
    Apiomat.Datastore.getInstance().loadCountFromServer(Apiomat.ausstattung, undefined, query, callback);
}


/* inheritance */
Apiomat.ausstattung.prototype = new Apiomat.AbstractClientDataModel();
Apiomat.ausstattung.prototype.constructor = Apiomat.ausstattung;

Apiomat.ausstattung.prototype.init=function () {
        this.data = new Object();
}
/**
 * get simple name
 * @return {string} className
 */
Apiomat.ausstattung.prototype.getSimpleName = function() {
    return "ausstattung";
};
/**
 * get module name
 * @return {string} moduleName
 */
Apiomat.ausstattung.prototype.getModuleName = function() {
    return "SpielplatzMain";
};

/* easy getter and setter */

        
/**
 * get Ausstattungsid
 * @return Ausstattungsid
 */
Apiomat.ausstattung.prototype.getAusstattungsid = function() 
{
    return this.data.ausstattungsid;
};

/**
 * set Ausstattungsid
 * @param Ausstattungsid
 */
Apiomat.ausstattung.prototype.setAusstattungsid = function(_ausstattungsid) {
    this.data.ausstattungsid = _ausstattungsid;
};

        
/**
 * get Ballsport
 * @return Ballsport
 */
Apiomat.ausstattung.prototype.getBallsport = function() 
{
    return this.data.ballsport;
};

/**
 * set Ballsport
 * @param Ballsport
 */
Apiomat.ausstattung.prototype.setBallsport = function(_ballsport) {
    this.data.ballsport = _ballsport;
};

        
/**
 * get Federwippe
 * @return Federwippe
 */
Apiomat.ausstattung.prototype.getFederwippe = function() 
{
    return this.data.federwippe;
};

/**
 * set Federwippe
 * @param Federwippe
 */
Apiomat.ausstattung.prototype.setFederwippe = function(_federwippe) {
    this.data.federwippe = _federwippe;
};

        
/**
 * get Klettergerüst
 * @return Klettergerüst
 */
Apiomat.ausstattung.prototype.getKlettergerüst = function() 
{
    return this.data.klettergerüst;
};

/**
 * set Klettergerüst
 * @param Klettergerüst
 */
Apiomat.ausstattung.prototype.setKlettergerüst = function(_klettergerüst) {
    this.data.klettergerüst = _klettergerüst;
};

        
/**
 * get Kletterturm
 * @return Kletterturm
 */
Apiomat.ausstattung.prototype.getKletterturm = function() 
{
    return this.data.kletterturm;
};

/**
 * set Kletterturm
 * @param Kletterturm
 */
Apiomat.ausstattung.prototype.setKletterturm = function(_kletterturm) {
    this.data.kletterturm = _kletterturm;
};

        
/**
 * get Rutsche
 * @return Rutsche
 */
Apiomat.ausstattung.prototype.getRutsche = function() 
{
    return this.data.rutsche;
};

/**
 * set Rutsche
 * @param Rutsche
 */
Apiomat.ausstattung.prototype.setRutsche = function(_rutsche) {
    this.data.rutsche = _rutsche;
};

        
/**
 * get Sandkasten
 * @return Sandkasten
 */
Apiomat.ausstattung.prototype.getSandkasten = function() 
{
    return this.data.sandkasten;
};

/**
 * set Sandkasten
 * @param Sandkasten
 */
Apiomat.ausstattung.prototype.setSandkasten = function(_sandkasten) {
    this.data.sandkasten = _sandkasten;
};

        
/**
 * get Sanitäranlagen
 * @return Sanitäranlagen
 */
Apiomat.ausstattung.prototype.getSanitäranlagen = function() 
{
    return this.data.sanitäranlagen;
};

/**
 * set Sanitäranlagen
 * @param Sanitäranlagen
 */
Apiomat.ausstattung.prototype.setSanitäranlagen = function(_sanitäranlagen) {
    this.data.sanitäranlagen = _sanitäranlagen;
};

        
/**
 * get Schaukel
 * @return Schaukel
 */
Apiomat.ausstattung.prototype.getSchaukel = function() 
{
    return this.data.schaukel;
};

/**
 * set Schaukel
 * @param Schaukel
 */
Apiomat.ausstattung.prototype.setSchaukel = function(_schaukel) {
    this.data.schaukel = _schaukel;
};

        
/**
 * get Seilbahn
 * @return Seilbahn
 */
Apiomat.ausstattung.prototype.getSeilbahn = function() 
{
    return this.data.seilbahn;
};

/**
 * set Seilbahn
 * @param Seilbahn
 */
Apiomat.ausstattung.prototype.setSeilbahn = function(_seilbahn) {
    this.data.seilbahn = _seilbahn;
};

        
/**
 * get Tischtennis
 * @return Tischtennis
 */
Apiomat.ausstattung.prototype.getTischtennis = function() 
{
    return this.data.tischtennis;
};

/**
 * set Tischtennis
 * @param Tischtennis
 */
Apiomat.ausstattung.prototype.setTischtennis = function(_tischtennis) {
    this.data.tischtennis = _tischtennis;
};

        
/**
 * get Wasserspiele
 * @return Wasserspiele
 */
Apiomat.ausstattung.prototype.getWasserspiele = function() 
{
    return this.data.wasserspiele;
};

/**
 * set Wasserspiele
 * @param Wasserspiele
 */
Apiomat.ausstattung.prototype.setWasserspiele = function(_wasserspiele) {
    this.data.wasserspiele = _wasserspiele;
};

        
/**
 * get Wippe
 * @return Wippe
 */
Apiomat.ausstattung.prototype.getWippe = function() 
{
    return this.data.wippe;
};

/**
 * set Wippe
 * @param Wippe
 */
Apiomat.ausstattung.prototype.setWippe = function(_wippe) {
    this.data.wippe = _wippe;
};


})(typeof exports === 'undefined' ? Apiomat
        : exports);

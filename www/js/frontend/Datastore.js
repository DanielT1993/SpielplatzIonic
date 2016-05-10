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
if (typeof goog !== 'undefined')
{
	goog.provide('Apiomat.Datastore');

	goog.require('Apiomat.AOMHelper');
	goog.require('Apiomat.ApiomatRequestError');
	goog.require('base64');
	goog.require('XMLHttpRequest');
}

if(typeof exports === 'undefined')
{
	var Apiomat = Apiomat || {};
}

(function(Apiomat)
{

Apiomat.AOMOfflineStrategy = {
	"NO_OFFLINE_HANDLING" : 0,
	"USE_OFFLINE_CACHE" : 1,
};

Apiomat.AOMAuthType = {
	/**
	 * Configuration without credentials.
	 */
	GUEST : 0,
	/**
	 * Configuration with username and password
	 */
	USERNAME_PASSWORD : 1,
	/**
	 * Configuration with an OAuth2 token
	 */
	OAUTH2_TOKEN : 2

};

/* Constants for SSO */
Apiomat.SSO_REDIRECT_URL = "AOM_SSO_REDIRECT_URL";
Apiomat.SSO_REDIRECT_DATA = "AOM_SSO_REDIRECT_DATA";

Apiomat.HEADER =
{
	ETAG           : "Etag",
	LAST_MODIFIED  : "Last-Modified",
	MODIFIED_SINCE : "If-Modified-Since",
	EXPIRES        : "Expires",
	CACHE_CONTROL  : "Cache-Control"
};

Apiomat.Datastore = (function()
{
	var instantiated;
	var _password;
	var _username;
	var _baseURL;
	var _apiKey;
	var _system;
	var _version;
	var _useASync = true;
	var _offlineStrategy = Apiomat.AOMOfflineStrategy.NO_OFFLINE_HANDLING;
	var _offlineHandler = undefined;
	var _authType = Apiomat.AOMAuthType.GUEST;
	var _sessionToken;
	var _checkObjectState=undefined;
	var _idpHosts = [];

	/* is safari browser */
	var is_safari = Apiomat.AOMHelper.isSafari();
	/* or is it titanium */
	var is_titanium = Apiomat.AOMHelper.isTitaniumApp();
	/* or is node.js and NOT Titanium */
	var is_nodejs = Apiomat.AOMHelper.isNodeJS();

	var is_ie = Apiomat.AOMHelper.detectIE();

	/*
	 * we have to save lastModified timestamps for safari browsers cause safari
	 * send always seconds instead of ms
	 */
	var lastModified = new Array();
	var eTag = Array();

	/**
	 * Call the "onOk" function as callback
	 * returnValue is passed to this function (optional)
	 */
	function positiveCallback (_callback, returnValue, meta)
	{
		if (typeof _callback!=="undefined" && _callback!={})
		{
			for (var func in _callback)
			{
				var cbStatement="onok";
				var onok=typeof(_callback[func]) == "function"&&func.toString().toLowerCase()==cbStatement?func.toString():undefined;
				if (typeof onok!="undefined")
				{
					if(returnValue && typeof returnValue!="undefined" && returnValue !== null)
					{
						_callback[onok](returnValue, meta);
					}
					else
					{
						_callback[onok](undefined, meta);
					}
					break;
				}
			}
		}
	}

	/**
	 * Call the "onError" function as callback
	 * returnValue (for example: error) is passed to this function (optional)
	 */
	function negativeCallback (_callback, returnValue, meta)
	{
		if (typeof _callback!=="undefined" && _callback!={})
		{
			for (var func in _callback)
			{
				var cbStatement="onerror";
				var onerror=typeof(_callback[func]) == "function"&&func.toString().toLowerCase()==cbStatement?func.toString():undefined;
				if (typeof onerror!="undefined")
				{
					if(returnValue && typeof returnValue!="undefined")
					{
						_callback[onerror](returnValue);
					}
					else
					{
						_callback[onerror]();
					}
					break;
				}
			}
		}
	}

	/**
	 * Configure Datastore instance with given credentials
	 */
	function configure(baseURL, apiKey, system, version, username, password, sessionToken)
	{
		_baseURL = baseURL;
		_apiKey = apiKey;
		_system = system;
		_version = version;
		_username = username;
		_password = password;
		_sessionToken = sessionToken;
		/* set authType */
		if(typeof _sessionToken !== 'undefined')
		{
			_authType = Apiomat.AOMAuthType.OAUTH2_TOKEN;
		}
		else if(typeof _username !== 'undefined' && typeof _password !== 'undefined')
		{
			_authType = Apiomat.AOMAuthType.USERNAME_PASSWORD;
		}
		else
		{
			_authType = Apiomat.AOMAuthType.GUEST;
		}

		//initiate objectstate check
		_checkObjectState = true;
	}

	function init()
	{
		// all singleton code goes here
		return {

			getUsername : function()
			{
				return _username;
			},

			getPassword : function()
			{
				return _password;
			},

			getAuthType : function()
			{
				return _authType;
			},

			setUseAsnycRequests : function(_asyncReq)
			{
				_useASync = _asyncReq;
			},

			loadFromServer : function(modelHref, callback, dataModel,withReferencedHrefs, _query,
					clazz)
			{
			var isReload = dataModel && modelHref === dataModel.getHref();

			_sendRequest("GET", modelHref, [ 200, 304 ],!dataModel ? callback : {
						onOk : function(obj, meta)
						{
							if(obj && meta && meta.status !== 304) {
								dataModel.fromJson(obj);
							}
							else if(meta.status === 304) {
								dataModel = dataModel;
							}
							Apiomat.Datastore.positiveCallback(callback, meta && meta.status == 304 ? dataModel : undefined, meta);
						},

						onError : function(error, meta)
						{
							Apiomat.Datastore.negativeCallback(callback, error, meta);
						}
					}, clazz, {
						"isReload" : isReload,
						"data" : _query,
						"withReferencedHrefs":withReferencedHrefs
					});
			},
			getOrCreateUser: function (facebookToken,callback) {
				var appName = Apiomat.User.AOMBASEURL.substring( Apiomat.User.AOMBASEURL.lastIndexOf( '/' ) + 1 );
		        var url = _baseURL.substring(0, _baseURL.indexOf( 'yambas/rest' ) + 11 ) + '/modules/facebook/spec/' + appName + '/aomuser' + '?facebookToken=' + facebookToken;
		        _sendRequest("GET", url, [200],callback, undefined, undefined);
			},

			setCheckObjectState : function (checkObjectState)
			{
				_checkObjectState = checkObjectState;
			},
			getCheckObjectState : function ()
			{
				return _checkObjectState;
			},
			loadResource : function(_url, _callback)
			{
				_sendRequest("GET", _url, [ 200, 206 ], _callback, undefined, {
					"isByteData" : true
				});
			},

			loadListFromServerWithClass : function(_class, _query, _callback,withReferencedHrefs)
			{
				_sendRequest("GET", _createHrefFromClass(_class), [ 200, 304 ],
						_callback, _class, {
							"data" : _query,
							"withReferencedHrefs":withReferencedHrefs
						});
			},

			postOnServer : function(dataModel, callback, href)
			{
				_postOnServer(dataModel, callback, href);
			},
			updateOnServer : function(dataModel, callback)
			{
				_updateOnServer(dataModel.getHref(), dataModel.toJson(), callback);
			},
			updateOnServerWithJSON : function(href, json, callback)
			{
				_updateOnServer(href, json, callback);
			},
			deleteOnServer : function(_href, _callback)
			{
				_deleteOnServer(_href, undefined, _callback);
			},
			deleteModelOnServer : function(_dataModel, _callback)
			{
				_deleteOnServer(undefined, _dataModel, _callback);
			},
			postStaticDataOnServer : function(_data, _isImage, _callback)
			{
				var href = _createStaticDataHref(_isImage);
				this.postStaticDataOnServerWithHref(_data, href, _callback);
			},
			postStaticDataOnServerWithHref : function(_data, _href, _callback)
			{
				_sendRequest("POST", _href, [ 201 ], _callback, undefined, {
					"isByteData" : true,
					"data" : _data
				});
			},

			/* methods provided for offline handling */

			shouldSendOffline : function(_httpMethod)
			{
				var useOffline = this.getOfflineStrategy() != Apiomat.AOMOfflineStrategy.NO_OFFLINE_HANDLING
						&& this.getOfflineHandler() != undefined
						&& this.getOfflineHandler().isConnected() === false;
				/*
				 * switch(Apiomat.Datastore._offlineStrategy) { case
				 * Apiomat.AOMOfflineStrategy.USE_OFFLINE_CACHE: useOffline &=
				 * true; break; default: useOffline = false; break; }
				 */

				return useOffline;
			},

			getOfflineHandler : function()
			{
				return _offlineHandler;
			},

			getOfflineStrategy : function()
			{
				return _offlineStrategy;
			},

			/**
			 * Add the request to the request queue. This queue will be
			 * processed after internet connection is back
			 *
			 * @param _httpMethod
			 *            http method name as String (POST, DELETE, PUT)
			 * @param _href
			 *            (optional) Href of class instance / static content (only used for
			 *            PUT/DELETE)
			 * @param _content
			 *            (optional) Content which will be sended to server
			 * @param _param
			 *            (optional) Can contain the reference name (on
			 *            creating reference between 2 models), a boolean (which
			 *            indicates if static content is an image or not) or
			 *            undefined
			 * @param _callback
			 *            (optional) the method that will be called after adding
			 *            request to queue is finished
			 */
			sendOffline : function(_httpMethod, _href, _content, _param,
					_callback)
			{
				var err = undefined;
				var isStaticData = false;
				/*
				 * if no href is given and content is of type
				 * AbstractClientDataModel try to create one
				 */
				if (!_href
						&& _content instanceof Apiomat.AbstractClientDataModel)
				{
					_href = _createModelHref(_content);
				}
				/*
				 * If there is no href and _content is not of type ACDM then we
				 * assume it is static content like image/file
				 */
				else if (!_href)
				{
					_href = _createStaticDataHref(_param);
					isStaticData = true;
				}

				var errorFunc = function(error)
				{
					if (typeof _callback !== 'undefined')
					{
						/* an error occured because there is no returned href */
						if (_httpMethod === "POST")
						{
							error = new Apiomat.ApiomatRequestError(
									Apiomat.Status.CRUD_ERROR, 201,
									"Can't save offline");
						}
						Apiomat.Datastore.negativeCallback(_callback,error);
					} else if (typeof console !== 'undefined' && console.error)
					{
						console.error("Can't send request to offline queue: "
								+ error);
					}
				};
				var successFunc = function(returnedHref)
				{

					if (_httpMethod === "POST" && returnedHref)
					{
						Apiomat.Datastore.positiveCallback(_callback, returnedHref);
					} else
					{
						Apiomat.Datastore.positiveCallback(_callback);
					}
				};
				/* send to offline queue */
				this.getOfflineHandler().addTask(_httpMethod, _href, _content,
						_param, successFunc, errorFunc);
			},

			/**
			 * If successful, the callback contains a map with tokens and additional info
			 *
			 * @param callback methods which will called after request is finished
			 */
			requestSessionToken : function( _callback )
			{
				if (_authType !== Apiomat.AOMAuthType.USERNAME_PASSWORD)
				{
					var error = new Apiomat.ApiomatRequestError(Apiomat.Status.BAD_DATASTORE_CONFIG);
					if (typeof _callback !== 'undefined')
					{
						Apiomat.Datastore.negativeCallback(_callback,error);
					}
					else if (typeof console !== 'undefined' && console.error)
					{
						console.error("Error occured: " + error);
					}
				}
				else
				{
					var appName = _baseURL.substring( _baseURL.lastIndexOf( '/' ) + 1 );
					var params = {
						'grant_type' : 'aom_user',
						'client_id' : appName,
						'client_secret' : _apiKey,
						'scope' : 'read write',
						'username' : _username,
						'password' : _password,
						'app' : appName,
						'system' : _system
					};
					_requestSessionToken(params, _callback);
				}
			},

			/**
			 * If successful, the callback contains a map with tokens and additional info
			 *
			 * @param refreshToken The refresh token to use for requesting a new session token
			 * @param callback methods which will called after request is finished
			 */
			requestSessionTokenWithRefreshToken : function( refreshToken, callback  )
			{
				var appName = _baseURL.substring( _baseURL.lastIndexOf( '/' ) + 1 );
				var params = {
					'grant_type' : 'refresh_token',
					'client_id' : appName,
					'client_secret' : _apiKey,
					'refresh_token' : refreshToken
				};
				_requestSessionToken(params, callback);
			},

			/**
			 * Return count of models (or reference) from server (filtered by given _query)
			 */
			loadCountFromServer : function(_classOrHref, _refName, _query, _callback)
			{
				var url = undefined;
				/* Href was used */
				if(typeof _classOrHref === 'string' && typeof _refName !== 'undefined')
				{
					url = _classOrHref + '/' + _refName;
				}
				else
				{
					url = _createHrefFromClass(_classOrHref);
				}
				url += '/count';
				_sendRequest("GET", url, [ 200 ],
						{
							onOk : function(numStr) {
								/* Convert string to number */
								Apiomat.Datastore.positiveCallback(_callback,Number(numStr));
							},
							onError : function(error) {
								if(typeof _callback)
								{
									Apiomat.Datastore.negativeCallback(_callback,error);
								}
							}
						}, undefined, {
							"data" : _query,
							'returnpost' : true
				});
			},

			setIdPHosts : function (idpHosts)
			{
				_setIdPHosts(idpHosts);
			},
			getIdPHosts : function ()
			{
				return _getIdPHosts();
			}
		};
	}

	/* private functions */
	function _postOnServer(dataModel, callback, href)
	{
		if (!href)
		{
			href = _createModelHref(dataModel);
		}
		_sendRequest("POST", href, [ 201 ], callback, undefined, {
			"data" :  dataModel.toJson()
		});
	}

	function _updateOnServer (_href,_json, _callback )
	{
		_sendRequest("PUT", _href, [ 200 ], _callback, undefined,
		{
			"data" : _json
		});
	}

	function _deleteOnServer(_href, _dataModel, _callback)
	{
		if (_href)
		{
			_sendRequest("DELETE", _href, [ 204 ], _callback);
		} else if (_dataModel && _dataModel.getHref())
		{
			_sendRequest("DELETE", _dataModel.getHref(), [ 204 ], _callback);
		} else
		{
			var error = new Apiomat.ApiomatRequestError(
					Apiomat.Status.HREF_NOT_FOUND);
			if (_callback)
			{
				Apiomat.Datastore.negativeCallback(_callback,error);
			} else if (console && console.error)
			{
				console.error("Error occured: " + error);
			}
		}
	}

	/**
	 * If successful, the callback contains a map with tokens and additional info
	 *
	 * @param _params A list of NameValuePairs to build the url encoded form parameters
	 * @param _callback the callback method
	 */
	function _requestSessionToken( _params, _callback )
	{
		var url = _baseURL.substring(0, _baseURL.indexOf( 'yambas' ) + 6 ) + '/oauth/token';
		/* convert _params to key=value&key=value2.. */
		var data = '';
		for(prop in _params)
		{
			if(data !== '')
			{
				data += '&';
			}
			data += prop + '=' + _params[prop];
		}
		_sendRequest('POST', url,[ 200 ], {
			onOk: function(retData) {
				var result = {};
				var jsonRet = JSON.parse(retData) || {};
				result.sessionToken = jsonRet.access_token;
				result.refreshToken = jsonRet.refresh_token;
				var expirein = jsonRet.expires_in || 0;
				result.expirationDate = new Date().getTime() + expirein;
				result.module = jsonRet.aom_module;
				result.model = jsonRet.aom_model;
				Apiomat.Datastore.positiveCallback(_callback,result);
			},
			onError: function(error) {
				var e = new Apiomat.ApiomatRequestError(error.statusCode, 200, 'Requesting the session token failed');
				if(typeof _callback !== 'undefined')
				{
					Apiomat.Datastore.negativeCallback(_callback,e);
				}
				else if (console && console.error)
				{
					console.error("Error occured: " + e);
				}
			}
		}, undefined, {
			'data' : data,
			'returnpost' : true,
			'headerDetails' : {
					'authHeader' : false,
					'contentType' : 'application/x-www-form-urlencoded'
				},
		});
	}

	/**
	 * private method to send request to apiOmat backend
	 *
	 * @param _httpMethod
	 *            the http method (GET, POST, PUT, DELETE)
	 * @param _url
	 *            The url to connect
	 * @param _callback
	 *            object which will called when request is finished (needs 2
	 *            methods: onOk = function(arg) and onError =
	 *            function(errorCode, errorMsg))
	 * @param _expectedReturnCodes
	 *            the expected return code from HTTP request
	 * @param _args
	 *            Some optional arguments<br/>Valid key/values are: "data" =>
	 *            on POST/PUT the data to be send "isByteData" => set to true if
	 *            "data" field contains byte data (like image/video) "query" =>
	 *            for GET req we save query string here "clazz" => some object
	 *            which inherits from AbstractClientDataModel, where the result
	 *            will be saved
	 */
	function _sendRequest(_httpMethod, _url, _expectedReturnCodes, _callback, clazz, _args)
	{
		_args = _args || {};
		var data = _args.data || undefined; // could be query on GET or (json) data on POST/PUT
		var withReferencedHrefs = _args.withReferencedHrefs || undefined; // could be query on GET or (json) data on POST/PUT
		var isByteData = _args.isByteData || false;
		var returnpost = _args.returnpost || false;
		var headerDetails = _args.headerDetails || {};
		var isReload = _args.isReload;
		var http;
		// Code for titanium
		if (is_titanium)
		{
			Ti.API.log("Use HTTP client of titanium");
			http = Ti.Network.createHTTPClient({
				// function called when the response data is
				// available
				onload : function(e)
				{
					_processResponse(this, _expectedReturnCodes, _callback, _httpMethod, clazz, returnpost, _url);
				},
				// function called when an error occurs, including a
				// timeout
				onerror : function(e)
				{
					var errorMsg = e.error || '';
					if(errorMsg.indexOf('connection failure') > -1 )
					{
						/* Handle common connection error */
						var error = new Apiomat.ApiomatRequestError(
								Apiomat.Status.NO_NETWORK, _expectedReturnCodes,
								errorMsg);
						if (typeof _callback !== 'undefined')
						{
							Apiomat.Datastore.negativeCallback(_callback,error);
						}
						else
						{
							Ti.API.error("Received HTTP error: "
									+ error);
						}
					}
					else if (_expectedReturnCodes
									.indexOf(this.status) > -1)
					{
						throw e;
					}
					else
					{
						var error = new Apiomat.ApiomatRequestError(
								this.status, _expectedReturnCodes,
								this.responseText);
						if (typeof _callback !== 'undefined')
						{
							Apiomat.Datastore.negativeCallback(_callback,error);
						} else
						{
							Ti.API.error("Received HTTP error: "
									+ error);
						}
					}
				}
			});
		}
		// code for IE7+, Firefox, Chrome, Opera, Safari
		else if (typeof window === 'object' && window.XMLHttpRequest)
		{
			http = new XMLHttpRequest();
			// code for IE6, IE5
		}
		/* load xmlhttprequst lib */
		else if(is_nodejs)
		{
			http = new exports.XMLHttpRequest();
		}
		else
		{
			http = new ActiveXObject("Microsoft.XMLHTTP");
		}

		/* should be query data so append to URL */
		if (_httpMethod === "GET" && data)
		{
			_url += "?q=" + encodeURIComponent(data);
		}

		if (is_titanium==false && is_nodejs==false &&  is_safari==false) {
			var ieVersion=false;
			ieVersion=is_ie;
			// detection of internet explorer 10 (fix caching)
			if (_httpMethod=== "GET"&&ieVersion!=false && ieVersion<=10) {
				if (data) {
					_url +="&";
				} else {
					_url +="?";
				}
				_url +="dIE="+new Date().getTime();
			}
		}

		/* add withReferencedHrefs parameter */
		if (_httpMethod === "GET" && typeof withReferencedHrefs != "undefined" && withReferencedHrefs==true)
		{
			if (data || (ieVersion!=false && ieVersion<=10)) {
				_url +="&";
			} else {
				_url +="?";
			}
			_url += "withReferencedHrefs=" + withReferencedHrefs;
		}

		_url = _createHref(_url);

		http.open(_httpMethod, _url, _useASync);
		_setHeader(_url, _httpMethod, http, isByteData, headerDetails,isReload);
		if (isByteData && _httpMethod === "GET")
		{
			http.overrideMimeType('text/plain; charset=x-user-defined');
		}
		/* set http body if there any data */
		if(is_titanium == false)
		{
			http.onreadystatechange = function()
			{
				if (http.readyState == 4)
				{
					_processResponse(http, _expectedReturnCodes, _callback, _httpMethod, clazz, returnpost, _url);
				}
			};
		}
		if ((_httpMethod === "POST" || _httpMethod === "PUT")
				&& typeof data !== 'undefined')
		{
			/* let's if we have to send byte data instead of json */
			if (_args && _args.isByteData)
			{
				var bytes = data;
				if (is_titanium === false)
				{
					var uInt8Array = new Uint8Array(data);
					bytes = uInt8Array.buffer;
				}
				http.send(bytes);
			} else
			{
				http.send(data);
			}
		} else
		{
			http.send();
		}
	}

	function _processResponse(http, _expectedReturnCodes, _callback, _httpMethod, clazz, returnpost, _url)
	{
		var errorOccured = false;
		try
		{
			/* Check first if it is SAML redirect */
			var isSamlRedirect = false;
			var responseURL = http.responseURL;

			/* Check if POST-POST SAML redirect */
			var contentType = http.getResponseHeader('Content-Type');
			var ssoURL = undefined;
			if(contentType && contentType.indexOf('text/html') > -1)
			{
				ssoURL = _isSSOHost(http);
				if(typeof ssoURL !== 'undefined')
				{
					isSamlRedirect = true;
					/* throw SSO exception with provided data */
					var error = new Apiomat.ApiomatRequestError(
							Apiomat.Status.SSO_REDIRECT, _expectedReturnCodes,
							null);
					var ssoInfos = {};
					ssoInfos[Apiomat.SSO_REDIRECT_URL] = ssoURL;
					ssoInfos[Apiomat.SSO_REDIRECT_DATA] = http.responseText;
					error.setExtraInformations(ssoInfos);
					if (typeof _callback !== 'undefined' && _callback.onError)
					{
						_callback.onError(error, {status: http.status});
					}
					return;
				}
			}
			/* check if status code in expected ones */
			if (_expectedReturnCodes.indexOf(http.status) > -1)
			{
				if (_callback && _callback.hasOwnProperty('onOk'))
				{
					/* save lastModified for url if safari or ie, additionally the etag, if there is one */
					if ((is_safari || is_ie ) && _httpMethod === "GET")
					{
						lastModified[_url] = http.getResponseHeader("Last-Modified");
						if(is_ie)
						{
							eTag[_url] = http.getResponseHeader(Apiomat.HEADER.ETAG);
						}
					}

					var elem = http.responseText;
					if (_httpMethod === "GET"
							&& typeof clazz !== 'undefined')
					{
						/* check if array */
						var json = JSON.parse(elem);
						if (json instanceof Array)
						{
							elem = [];
							for (var i = 0; i < json.length; i++)
							{
								var tmpElem = new clazz();
								tmpElem.fromJson(json[i]);
								elem.push(tmpElem);
							}
						} else
						{
							elem = new clazz();
							elem.fromJson(json);
						}

						/*
						//#AOM-735 implement caching in safari
						if (is_safari && _offlineStrategy != Apiomat.AOMOfflineStrategy.NO_OFFLINE_HANDLING)  {
							//save request to map
							for (var i=0;i<json.length;i++) {
								_safariCache[json[0].href]=json[0];
							}
						}
						*/
					}
					var returnedHref = undefined;
					if (_httpMethod === "POST" && returnpost === false)
					{
						returnedHref = http.getResponseHeader("Location");
					}
					Apiomat.Datastore.positiveCallback(_callback, _httpMethod === "GET" || returnpost ? elem: returnedHref || undefined, {status: http.status});
				}
			} else
			{
				errorOccured = true;
			}
		} catch (ex)
		{
			if(is_titanium)
			{
				Ti.API.log("Error occured: " + ex);
			}
			/* only if it is a error that has nth to do with apiOmat rethrow*/
			if(http && _expectedReturnCodes.indexOf(http.status) > -1)
			{
				throw ex;
			}
			else
			{
				errorOccured = true;
			}
		} finally
		{
			/* check if we have to set error */
			if (errorOccured)
			{
				console.log("Status: "+ http.status);
				var error = new Apiomat.ApiomatRequestError(http.status, _expectedReturnCodes,http.responseText);
				if (typeof _callback !== 'undefined')
				{
					Apiomat.Datastore.negativeCallback(_callback, error, {status: http.status});
				} else
				{
					if (typeof console !== 'undefined' && console.error)
					{
						console.error("Error occured: " + error);
					}
				}
			}
		}
	}

	function _setHeader(_url, _httpMethod, http, _isByteData, headerDetails, isReload)
	{
		http.setRequestHeader("X-apiomat-system", _system);
		http.setRequestHeader("X-apiomat-apikey", _apiKey);
		if(headerDetails.contentType)
		{
			http.setRequestHeader("Content-Type", headerDetails.contentType);
		}
		else if (typeof _isByteData !== 'undefined' && _isByteData)
		{
			http.setRequestHeader("Content-Type", "application/octet-stream");
		}
		else
		{
			http.setRequestHeader("Content-Type", "application/json");
			http.setRequestHeader("Accept", "application/json");
		}
		http.setRequestHeader("X-apiomat-fullupdate", "true");
		/* Set correct Auth header depending on AuthType */
		if(headerDetails.hasOwnProperty('authHeader') && headerDetails.authHeader == false)
		{
			/* dont set auth header if not wanted */
		}
		else if (_authType === Apiomat.AOMAuthType.USERNAME_PASSWORD)
		{
			var creds = _username + ":" + _password;
			if(is_nodejs)
			{
				try
				{
					var buffer = require('buffer').Buffer;
					creds = new buffer(creds).toString('base64');
				}
				catch (e) {
					/* can't find buffer try old method */
					creds = Base64.Base64.encode(creds);
				}
			}
			else
			{
				try
				{
					creds = Base64.encode(creds);
				}
				catch (e) {
					/* can't find buffer try old method */
					creds = Base64.Base64.encode(creds);
				}
			}
			http.setRequestHeader("Authorization", "Basic "
					+ creds);
		}
		else if(_authType === Apiomat.AOMAuthType.OAUTH2_TOKEN)
		{
			http.setRequestHeader("Authorization", 'Bearer ' + _sessionToken);
		}

		http.setRequestHeader("X-apiomat-sdkVersion", _version);
		/*
		 * send saved lastModified for safari browser (cause safari uses seconds
		 * and not returned modified hedaer)
		 */
		if ((is_safari || is_titanium) && _httpMethod === "GET")
		{
			// disabled caching for Safari cause we don't get JSON element in
			// http.responseText on 304
			//var lastModifiedStr = _url in lastModified? lastModified[_url] :
			// "01.01.1970 00:00:00:00 CEST";
			var lastModifiedStr = "01.01.1970 00:00:00:00 CEST";
			http.setRequestHeader("If-Modified-Since", lastModifiedStr);
		}
		//handling the strange ie caching behaviour
		else if(is_ie && _httpMethod === "GET")
		{
			//deactivate ies default caching at first
			http.setRequestHeader(Apiomat.HEADER.MODIFIED_SINCE, 'Wed, 02 Mar 1970 12:00:00 GMT');
			http.setRequestHeader(Apiomat.HEADER.EXPIRES, -1);
			http.setRequestHeader(Apiomat.HEADER.CACHE_CONTROL, 'must-revalidate, private');

			if(isReload)
			{
				if(eTag[_url])
				{
					http.setRequestHeader("If-None-Match", eTag[_url]);
				}
				if(lastModified[_url] !== null)
				{
					http.setRequestHeader(Apiomat.HEADER.LAST_MODIFIED, lastModified[_url]);
				}
			}
		}
	}

	function _createModelHref(_dataModel)
	{
		var href = _baseURL;
		href += "/models/";
		href += _dataModel.getModuleName();
		href += "/";
		href += _dataModel.getSimpleName();
		return href;
	}
	;

	function _createHrefFromClass(_class)
	{
		var href = _baseURL;
		href += "/models/";
		href += _class.prototype.getModuleName.call();
		href += "/";
		href += _class.prototype.getSimpleName.call();
		return href;
	}

	function _createStaticDataHref(isImage)
	{
		var href = _baseURL;
		href += "/data/";
		href += isImage ? "images" : "files";
		href += "/";
		return href;
	}

	function _createHref(href)
	{
		if (href.substring(0, 4) === 'http')
		{
			return href;
		}

		if (href.substring(0, 5) === "/apps")
		{
			return _baseURL.substring(0, _baseURL.indexOf("/apps")) + href;
		}

		return _baseURL + "/" + href;
	}

	/**
	 * Check if HTTP response could be SSO redirect
	 */
	function _isSSOHost(http)
	{
		var ssoURL = undefined;
		var content = http.responseText;
		/* Check if there is any configured SSO host in action of form */
		var regex = /<form.+method.?=.?"POST".+?action=.?"(.*?)".*?>/g;
		var matches;
		while (match = regex.exec(content))
		{
			/* Check if any configured ssoHost is contained in match */
			for ( var i in _idpHosts)
			{
				if(match[1].indexOf(_idpHosts[i]) > -1)
				{
					ssoURL = match[1];
					break;
				}
			}
		}
		return ssoURL;
	}

	function _setIdPHosts(idpHosts)
	{
		_idpHosts = idpHosts;
	}

	function _getIdPHosts ()
	{
		return _idpHosts;
	}

	return {
		getInstance : function()
		{
			if (!instantiated)
			{
				instantiated = init();
			}
			return instantiated;
		},

		isInstantiated : function()
		{
			return typeof instantiated !== 'undefined' && instantiated;
		},

		positiveCallback : function(_callback, returnValue, meta)
		{
			positiveCallback(_callback, returnValue, meta);
		},

		negativeCallback : function(_callback, returnValue, meta)
		{
			negativeCallback(_callback,returnValue, meta);
		},

		/*
		 * @Deprecated
		 */
		configure : function(user)
		{
			this.configureWithCredentials(user);
		},
		configureWithCredentials : function(user)
		{
			configure(Apiomat.User.AOMBASEURL, Apiomat.User.AOMAPIKEY, Apiomat.User.AOMSYS, Apiomat.User.AOMSDKVERSION, user.getUserName(), user.getPassword());
		},
		configureAsGuest : function(baseURL, apiKey, system, version)
		{
			configure(baseURL, apiKey, system, version);
		},
		configureWithUserSessionToken : function(user)
		{
			this.configureWithSessionToken(user.getSessionToken());
		},
		configureWithSessionToken: function(sessionToken)
		{
			configure(Apiomat.User.AOMBASEURL, Apiomat.User.AOMAPIKEY, Apiomat.User.AOMSYS, Apiomat.User.AOMSDKVERSION, undefined, undefined, sessionToken);
		},
		/*
		 * @Deprecated
		 */
		configurePlain : function(baseURL, apiKey, system, version)
		{
			this.configureAsGuest(baseURL, apiKey, system, version);
		},

		/**
		 * Define the offline strategy for this application Please set callback
		 * method as parameter so that you will be informed after initalization
		 * was successfull
		 */
		setOfflineStrategy : function(offlineStrategy, _callback)
		{
			_offlineStrategy = offlineStrategy;
			/* init offline Handler if not there */
			if (!_offlineHandler
					&& _offlineStrategy != Apiomat.AOMOfflineStrategy.NO_OFFLINE_HANDLING)
			{
				_offlineHandler = new Apiomat.AOMOfflineHandler();
			}
			if (!_offlineHandler
					&& _offlineStrategy != Apiomat.AOMOfflineStrategy.NO_OFFLINE_HANDLING)
			{
				_offlineHandler = new Apiomat.AOMOfflineHandler();
			}
			if (typeof _offlineHandler !== 'undefined')
			{
				if (typeof _callback !== 'undefined')
				{
					_offlineHandler.init(_callback.onOk || undefined,
							_callback.onError || undefined);
				} else
				{
					_offlineHandler.init(undefined, undefined);
				}
			}
		}
	};
})();
})(typeof exports === 'undefined'? Apiomat : exports);

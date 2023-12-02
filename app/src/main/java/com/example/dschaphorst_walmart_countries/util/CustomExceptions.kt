package com.example.dschaphorst_walmart_countries.util

/**
 * Set of Custom Exception classes used to better control Exception responses.
 *
 * @constructor
 * Message string
 *
 * @param message The message of the Exception
 */

class NullResponseFromServer(message: String) : Exception(message)
class FailureResponseFromServer(message: String?) : Exception(message)
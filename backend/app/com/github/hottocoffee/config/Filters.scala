package com.github.hottocoffee.config

import jakarta.inject.{Inject, Singleton}
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter

@Singleton
class Filters @Inject()(corsFilter: CORSFilter) extends DefaultHttpFilters(corsFilter)

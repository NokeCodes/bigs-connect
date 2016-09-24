#!/usr/bin/ruby

require 'sinatra'
require 'config'

class App
  def initialize
    @connection = PG::connection.new(Configuration::DB)
    
  end

  def helloRoute
    'hello, world'
  end
end

app = App.new()

get '/' do
  app.helloRoute
end




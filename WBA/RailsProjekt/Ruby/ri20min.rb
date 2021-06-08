#!/usr/bin/env ruby

class MegaGreeter
  attr_accessor :names

  def initialize(names = "Welt")
    @names = names
  end

  def sag_hallo
    if @names.nil?
      puts "..."
    elsif @names.respond_to?("each")
      @names.each do |name|
        puts "Zum Gruße, #{name}!"
      end
    else
      puts "Vallah gut dich zu sehen Habibi, #{@names}!"
    end
  end

  def sag_tschuess
    if @names.nil?
      puts "..."
    elsif @names.respond_to?("join")
      puts "San Frantschüssko, #{@names.join(", ")}"
    else
      puts "Tschuesseldorf, #{@names}"
    end
  end

end


if __FILE__ == $0
  mg = MegaGreeter.new
  mg.sag_hallo
  mg.sag_tschuess

  mg.names = "Prinz Ferdinand von und zu und überhaupt"
  mg.sag_hallo
  mg.sag_tschuess

  mg.names = ["Jessica", "Sylvana", "Sarafina",
    "Lavinia", "Sarah-Jane", "Calantha", "Estefania", "Loredana",
    "Sascha", "Patrick", "Jeremy-Pascal"]
  mg.sag_hallo
  mg.sag_tschuess

  mg.names = nil
  mg.sag_hallo
  mg.sag_tschuess
end

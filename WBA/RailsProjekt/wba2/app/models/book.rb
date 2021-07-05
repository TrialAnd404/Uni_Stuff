class Book < ApplicationRecord
	has_many: book_instances, dependent: :destroy
	has_and_belongs_to_many: authors

	validates :title, :publisher, :edition, :isbn presence: true
	validates :isbn, 
		:uniqueness => true,
		format: { multiline: true, with: /^(?=[0-9X]{10}$|(?=(?:[0-9]+[-\ ]){3})[-\ 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[-\ ]){4})[-\ 0-9]{17}$)(?:97[89][-\ ]?)?[0-9]{1,5}[-\ ]?[0-9]+[-\ ]?[0-9]+[-\ ]?[0-9X]$/}
		# (?=                       # Basic format pre-checks (lookahead):
		# 	[0-9X]{10}$             #   Require 10 digits/Xs (no separators).
		# |                        #  Or:
		# 	(?=(?:[0-9]+[-\ ]){3})  #   Require 3 separators
		# 	[-\ 0-9X]{13}$          #     out of 13 characters total.
		# |                        #  Or:
		# 	97[89][0-9]{10}$        #   978/979 plus 10 digits (13 total).
		# |                        #  Or:
		# 	(?=(?:[0-9]+[-\ ]){4})  #   Require 4 separators
		# 	[-\ 0-9]{17}$           #     out of 17 characters total.
		# )                         # End format pre-checks.
		# (?:97[89][-\ ]?)?         # Optional ISBN-13 prefix.
		# [0-9]{1,5}[-\ ]?          # 1-5 digit group identifier.
		# [0-9]+[-\ ]?[0-9]+[-\ ]?  # Publisher and title identifiers.
		# [0-9X]                    # Check digit.
		# $
	protected
		def self.custom_select(filter)
			books =  Book.order('pub_year asc')
			case filter
			when 'not_in_stock'
				books = books.select {|a| BookInstance.where("book_id = #{a.id}").count == 0}
			end	
			return books		
		end

		def toString
		
		end
end

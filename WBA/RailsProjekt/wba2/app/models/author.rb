class Author < ApplicationRecord
	has_and_belongs_to_many :book, dependent: :destroy

	validates :family_name, :given_name, :affiliation presence: true

	protected
		def self.custom_select(filter)
			# alternativ mit “scope”
			authors = Author.order('family_name asc')
			case filter
			when 'no_books'
				authors = authors.select {|a| a.books.empty?}
			end
			return authors
		end

		def short_name
			shortname = self.given_name[0]+". "+self.family_name
			return shortname
		end

		def toString
		
		end
end

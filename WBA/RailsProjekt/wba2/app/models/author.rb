class Author < ApplicationRecord
	has_and_belongs_to_many :book, dependent: :destroy

	validates :affiliation :presence => true
	validates :given_name, :presence => true
	validates :family_name, :presence => true
	validates :family_name, uniqueness: { scope: :first_name }
	validates :given_name, format: { with: /\A[a-zA-Z]+\z/, message: "only allows letters" }
	validates :family_name, format: { with: /\A[a-zA-Z]+\z/, message: "only allows letters" }

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

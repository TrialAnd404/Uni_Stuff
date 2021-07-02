class CreateBooks < ActiveRecord::Migration[6.1]
  def change
    create_table :books do |t|
      t.string :title
      t.string :publisher
      t.integer :pub_year
      t.integer :edition
      t.string :isbn

      t.timestamps
    end
  end
end

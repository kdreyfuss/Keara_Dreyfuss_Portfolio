

# INSTRUCTIONS:
#   This HW is meant to make you practice with dplyr
#     - Please answer all questions in a single dplyr chain (one set of code connected with %>%'s)
#     - Using only select, filter, mutate, group_by, summarise, arrange, top_n or head
#
#   I am giving you the tables from the world database that you used in HW 1 / SQL
#   For most of these, you have the SQL solutions so you are able to check yourself (I had to change a few questions but kept many the same)

# Question 0#
# - 5pts for replacing "YourNameHere" in the filename with your actual name

country = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_country.csv')
city = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_city.csv')
language = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_language.csv')

# INSTRUCTIONS:
#   This HW is meant to make you practice with dplyr
#     - Please answer all questions in a single dplyr chain (one set of code connected with %>%'s)
#     - Using only select, filter, mutate, group_by, summarise, arrange, top_n or head
#
#   I am giving you the tables from the world database that you used in HW 1 / SQL
#   For most of these, you have the SQL solutions so you are able to check yourself (I had to change a few questions but kept many the same)

# Question 0#
# - 5pts for replacing "YourNameHere" in the filename with your actual name

country = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_country.csv')
city = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_city.csv')
language = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_language.csv')

# Question 1 #
# - Load the dplyr library

library(dplyr)

# Question 2 #
# - Select the ID, Name, CountryCode, and Population variables from the city table
# - Save your results as a new dataset named city_small

city_small = city %>% 
  select(ID, Name, CountryCode, Population)

# Question 3 #
# - Select the ID, Name, CountryCode, and Population variables from the city table
# - Rename ID to CityID and Name to City
# - Save your results as a new dataset named city_small

city_small = city %>% 
  select(CityID = ID, City = Name, CountryCode, Population)

# Question 4 #
# - Select everything but District from the city table by removing it via select()
# - Save your results as a new dataset named city_small

city_small = city %>%
  select(-District)

# NOTE #
# You do not need to save results as a new dataset going forward, just let the results print.


# Question 5 #
# - Select CountryCode, Language, IsOfficial from the language table
# - Add a column that represents the number of speakers to this table

language  %>%
  select(CountryCode, Language, IsOfficial, Percentage, Population) %>% 
  mutate(Number_of_Speakers = Percentage/100*Population) %>% 
  select(-Population, -Percentage)


# Question 6 #
# - Return the CountryCode and Official Language of for each country from the language table

langauge %>% 
  select(CountryCode, Language, IsOfficial) %>%
  filter(IsOfficial == "TRUE") %>% 
  select(-IsOfficial)

# Question 7 #
# - Return the CountryCode, Official Language, and number of speakers of that language for each country

language  %>% 
  select(CountryCode, Language, IsOfficial, Percentage, Population) %>% 
  mutate(Number_of_Speakers = Percentage/100*Population) %>% 
  filter(IsOfficial == "TRUE") %>%
  select(-Population, -Percentage)

# Question 8 #
# - Return the name, population, and GNP for countries in North America (Continent)

country %>% 
  select(Name, Population, GNP, Continent) %>% 
  filter(Continent == "North America") %>%
  select(-Continent)


# Question 9 #
# - Return the Name, Population, and GNP for countries in North or South America that have a population over 30 million
# - HINT: %in% c('', '', ...) is R's version of the IN('', '', ...) that we used in SQL

country %>% 
  select(Name, Population, GNP, Continent, Population) %>% 
  filter(Population > 30000000 & Continent %in% c("North America", "South America")) %>%
  select(-Continent)

# Question 10 # 
# - Return Name, Population, and GNP for countries with populations between 50 and 100 million
 country %>% 
   select(Name, Population, GNP) %>% 
   filter(Population >= 50000000 & Population <= 100000000 )

# Question 11 #
# - How many countries is that (in #10)?
 country %>% 
   select(Name, Population, GNP) %>% 
   filter(Population >= 50000000 & Population <= 100000000 ) %>% 
   count()


# Question 12 #
# - What is the total worlwide population (based on population column in country table)?

 country %>%
   summarise(Total_Population = sum(Population))
   
# Question 13 #
# - What is the total population by continent?

 country %>% 
   group_by(Continent) %>% 
   summarise(Total_Population = sum(Population))

# Question 14 #
# - What is the total population by continent? Order results low to high by population.
 
 country %>% 
   group_by(Continent) %>% 
   summarise(Total_Population = sum(Population)) %>% 
   arrange(Total_Population)
 

# Question 15 #
# - What is the total population by continent? Order results high to low by population.

 country %>% 
   group_by(Continent) %>% 
   summarise(Total_Population = sum(Population)) %>% 
   arrange(desc(Total_Population))

# Question 16 #
# - Return a table that lists the total number of speakers for each language in the language table
# - Order results high to low by total number of speakers

language %>% 
  group_by(Language) %>% 
  summarise(Total_Speakers = sum(Population*Percentage/100)) %>% 
  arrange(desc(Total_Speakers)) 

# Question 17 #
# - Return a table that lists the total number of speakers for each language in the language table
# - Only return languages with at least 200M speakers
# - Order results high to low on total number of speakers

language %>%
  group_by(Language) %>% 
  summarise(TotalSpeakers = sum(Percentage/100*Population)) %>% 
  filter(TotalSpeakers >= 200000000) %>% 
  arrange(desc(TotalSpeakers))

# Question 18 #
# - Which Countries speak 10 or more languages?

language %>% 
  group_by(CountryCode) %>% 
  summarise(Number_of_Languages = n()) %>%
  filter(Number_of_Languages >= 10)

# Question 19 #
# - Return a table with the 10 most spoken languages in the world (by number of speakers)?
# - Order the results high to low on number of speakers

language %>% 
  group_by(Language) %>% 
  summarise(Total_Speakers = sum(Percentage/100*Population)) %>%
  top_n(10, Total_Speakers) %>% 
  arrange(desc(Total_Speakers))


# Question 20 #
# - Which 3 languages are spoken in the most countries?

language %>% 
  group_by(Language) %>% 
  summarise(Number_of_Countries = n_distinct(CountryCode)) %>% 
  top_n(3, Number_of_Countries)
  
# Question 21 #
# - Which language is spoken in exactly 15 different countries?

language %>% 
  group_by(Language) %>% 
  summarise(Number_of_Countries = n_distinct(CountryCode)) %>% 
  filter(Number_of_Countries == 15)

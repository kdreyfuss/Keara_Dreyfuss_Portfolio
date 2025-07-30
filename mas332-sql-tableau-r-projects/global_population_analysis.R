
# This project explores global population, language distribution, and economic indicators 
using the world_country, world_city, and world_language datasets. The analysis was performed entirely with
R using the dplyr package to demonstrate efficient, SQL-like data manipulation pipelines.



country = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_country.csv')
city = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_city.csv')
language = read.csv('https://dxl-datasets.s3.amazonaws.com/data/world_language.csv')
library(dplyr)

# - Select the ID, Name, CountryCode, and Population variables from the city table
city_small = city %>% 
  select(ID, Name, CountryCode, Population)

# - Select the ID, Name, CountryCode, and Population variables from the city table
# - Rename ID to CityID and Name to City

city_small = city %>% 
  select(CityID = ID, City = Name, CountryCode, Population)

# - Select everything but District from the city table by removing it via select()

city_small = city %>%
  select(-District)

# - Select CountryCode, Language, IsOfficial from the language table
# - Add a column that represents the number of speakers to this table

language  %>%
  select(CountryCode, Language, IsOfficial, Percentage, Population) %>% 
  mutate(Number_of_Speakers = Percentage/100*Population) %>% 
  select(-Population, -Percentage)

# - Return the CountryCode and Official Language of for each country from the language table

langauge %>% 
  select(CountryCode, Language, IsOfficial) %>%
  filter(IsOfficial == "TRUE") %>% 
  select(-IsOfficial)

# - Return the CountryCode, Official Language, and number of speakers of that language for each country

language  %>% 
  select(CountryCode, Language, IsOfficial, Percentage, Population) %>% 
  mutate(Number_of_Speakers = Percentage/100*Population) %>% 
  filter(IsOfficial == "TRUE") %>%
  select(-Population, -Percentage)

# - Return the name, population, and GNP for countries in North America (Continent)

country %>% 
  select(Name, Population, GNP, Continent) %>% 
  filter(Continent == "North America") %>%
  select(-Continent)

# - Return the Name, Population, and GNP for countries in North or South America that have a population over 30 million

country %>% 
  select(Name, Population, GNP, Continent, Population) %>% 
  filter(Population > 30000000 & Continent %in% c("North America", "South America")) %>%
  select(-Continent)

# - Return Name, Population, and GNP for countries with populations between 50 and 100 million
 country %>% 
   select(Name, Population, GNP) %>% 
   filter(Population >= 50000000 & Population <= 100000000 )

# - How many countries is that?
 country %>% 
   select(Name, Population, GNP) %>% 
   filter(Population >= 50000000 & Population <= 100000000 ) %>% 
   count()


# - What is the total worlwide population (based on population column in country table)?

 country %>%
   summarise(Total_Population = sum(Population))
   
# - What is the total population by continent?

 country %>% 
   group_by(Continent) %>% 
   summarise(Total_Population = sum(Population))

# - What is the total population by continent? Order results low to high by population.
 
 country %>% 
   group_by(Continent) %>% 
   summarise(Total_Population = sum(Population)) %>% 
   arrange(Total_Population)
 

# - What is the total population by continent? Order results high to low by population.

 country %>% 
   group_by(Continent) %>% 
   summarise(Total_Population = sum(Population)) %>% 
   arrange(desc(Total_Population))

# - Return a table that lists the total number of speakers for each language in the language table
# - Order results high to low by total number of speakers

language %>% 
  group_by(Language) %>% 
  summarise(Total_Speakers = sum(Population*Percentage/100)) %>% 
  arrange(desc(Total_Speakers)) 

# - Return a table that lists the total number of speakers for each language in the language table
# - Only return languages with at least 200M speakers
# - Order results high to low on total number of speakers

language %>%
  group_by(Language) %>% 
  summarise(TotalSpeakers = sum(Percentage/100*Population)) %>% 
  filter(TotalSpeakers >= 200000000) %>% 
  arrange(desc(TotalSpeakers))

# - Which Countries speak 10 or more languages?

language %>% 
  group_by(CountryCode) %>% 
  summarise(Number_of_Languages = n()) %>%
  filter(Number_of_Languages >= 10)

# - Return a table with the 10 most spoken languages in the world (by number of speakers)?
# - Order the results high to low on number of speakers

language %>% 
  group_by(Language) %>% 
  summarise(Total_Speakers = sum(Percentage/100*Population)) %>%
  top_n(10, Total_Speakers) %>% 
  arrange(desc(Total_Speakers))

# - Which 3 languages are spoken in the most countries?

language %>% 
  group_by(Language) %>% 
  summarise(Number_of_Countries = n_distinct(CountryCode)) %>% 
  top_n(3, Number_of_Countries)
  
# - Which language is spoken in exactly 15 different countries?

language %>% 
  group_by(Language) %>% 
  summarise(Number_of_Countries = n_distinct(CountryCode)) %>% 
  filter(Number_of_Countries == 15)

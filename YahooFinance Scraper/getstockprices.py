import requests
from bs4 import BeautifulSoup
import json

mystocks = ['VAST.L', 'ICON.L', 'PREM.L', 'BZT.L']
stockdata = []

def getData(symbol):
    headers = {'User-Agent' : 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36'}
    url = f'https://ca.finance.yahoo.com/quote/ASPL.L{symbol}'
    r = requests.get(url,headers=headers)

    soup = BeautifulSoup(r.text,'html.parser')
    
    stock = {
    'symbol': symbol,
    'price' : soup.find('span', {'class':'D(ib) Mend(20px)'}).find_all('span')[0].text,
    'change' : soup.find('span', {'class': 'D(ib) Mend(20px)'}).find_all('span')[1].text,
    }
    return stock

for item in mystocks:
    stockdata.append(getData(item))
    print('Getting:', item)

with open('stockdata.json','w') as f:
    json.dump(stockdata, f)

df = pd.read_json('stockdata.json')


df['price'] = pd.to_numeric(df['price'], errors='coerce')
df['change'] = pd.to_numeric(df['change'], errors='coerce')

print(df.describe())

plt.hist(df['price'], bins=10, alpha=0.5)
plt.title('Distribution of Stock Prices')
plt.xlabel('Price')
plt.ylabel('Frequency')
plt.show()

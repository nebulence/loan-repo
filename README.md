# loan-repo
This loan calculator contains 2 rest-api calls with simple calculation and amortization calculation. 

Simple calculation:
POST request call
/calculator/simple-calculation

request body:
{
    "amount": 1000,
    "interest": 5,
    "numberOfMonths": 8 
}
response body:
{
    "monthlyPayment": 127.36,
    "totalInterestPaid": 18.88
}

Amorization calculation, provides scheduled loan calculation:
POST request call
/calculator/amortization-calculation

request body:
{
    "amount": 3000,
    "interest": 5,
    "numberOfMonths": 5 
}
response body:
{
    "monthlyPayment": 607.52,
    "totalInterestPaid": 37.6,
    "payments": [
        {
            "paymentAmount": 607.52,
            "principalAmount": 595.02,
            "interestAmount": 12.5,
            "balanceOwed": 2404.98,
            "paymentIndex": 1
        },
        {
            "paymentAmount": 607.52,
            "principalAmount": 597.5,
            "interestAmount": 10.02,
            "balanceOwed": 1807.48,
            "paymentIndex": 2
        },
        {
            "paymentAmount": 607.52,
            "principalAmount": 599.99,
            "interestAmount": 7.53,
            "balanceOwed": 1207.49,
            "paymentIndex": 3
        },
        {
            "paymentAmount": 607.52,
            "principalAmount": 602.49,
            "interestAmount": 5.03,
            "balanceOwed": 605,
            "paymentIndex": 4
        },
        {
            "paymentAmount": 607.52,
            "principalAmount": 605,
            "interestAmount": 2.52,
            "balanceOwed": 0,
            "paymentIndex": 5
        }
    ]
}

Postman collection example: 
[postman_collections.zip](https://github.com/nebulence/loan-repo/files/8969284/postman_collections.zip)


Application clone from git:
git clone https://github.com/nebulence/loan-repo.git

clean and build app (for example in NetBeans), and then run

When application is up it runs on tomcat app server on [localhost](http://localhost:8080/), 
and H2 in memory database is up and can be set up on http://localhost:8080/h2-console/   (credentials and other setup can be found in application.properties) 


UML sequence diagram for Simple calculation:
![sequence diagram](https://user-images.githubusercontent.com/99651423/175358110-8062737e-5b4e-4ad9-9d8a-c97714e06dfe.png)

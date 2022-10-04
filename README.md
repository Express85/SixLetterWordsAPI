# SixLetterWordsAPI

- Build the application -> command: 'mvn clean install'
- Start the application -> command: 'java -jar target/SixLetterWordsAPI.jar '
- Use curl script: 'curl --request POST 'http://localhost:8080/api/file' --data-binary '@input.txt' --header "Content-Type: text/plain" >> output.txt'
  ( with 'curl --request POST 'http://localhost:8080/api/file' --data '@input.txt' --header "Content-Type: text/plain" >> output.txt' '--data' removed all line breaks

# 🤖 Demo Quiz with Android

Simple quiz application built with Android and Appwrite

## 🚀 Usage

- Clone the repository

```sh
git clone https://github.com/lohanidamodar/demo-quiz-with-android
```

- Import into Android Studio and run!

## Setup Project Dashboard

We need to setup some collections and need to define its attributes so our app will be ready to use.

In your appwrite console, head to Database tab. Click on Add Database, give it any name. After that add a new collection, this collection will store all the questions. Now, inside this collection, open Attributes tab. Add two string type attributes with names: **question** and **answer**, and add a string array attribute with name: **options**.

Now, add a couple of questions to this collection to complete the setup of console.

## Setup API IDs

You need to make some changes in `QuizViewModel.kt`:

- **url**: This is the endpoint URL. If you are testing the app on an android studio emulator and appwrite is configured on localhost, then you don't need to change it. Otherwise, you will need to change the hostname to the URL that you had set while initiating appwrite.

- **projectID**: can be found in project settings, in appwrite console.

- **databaseId**: can be found in collection settings, inside database tab, in appwrite console.

- **collectionId**: can be found in collection settings, inside database tab, in appwrite console.

**Now your Project is ready to run.**

## 🙌 Show your support

Drop a ⭐️ if this project helped you!

## 🤕 Support

If you get stuck anywhere, hop onto one of our [support channels in discord](https://appwrite.io/discord) and we'd be delighted to help you out 🤝

## 📝 License

Copyright © 2021 [Damodar Lohani]](https://github.com/lohanidamodar).<br />
This project is [MIT](./LICENSE.md) licensed.

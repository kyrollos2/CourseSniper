<h1 align="center">Course Sniper</h1>

<p align="center">
  <img src="https://github.com/kyrollos2/CourseSniper/blob/main/ReadMeBanner.png" alt="Project Banner">
</p>

<h4 align="center">🌟 Never Miss a Seat in Your Favorite Class Again! 🌟</h4>

<p align="center">
  <a href="https://github.com/kyrollos2/CourseSniper/issues">
    <img src="https://img.shields.io/github/issues/kyrollos2/CourseSniper.svg?style=flat-square" alt="GitHub issues">
  </a>
  <a href="https://github.com/kyrollos2/CourseSniper/network">
    <img src="https://img.shields.io/github/forks/kyrollos2/CourseSniper.svg?style=flat-square" alt="GitHub forks">
  </a>
  <a href="https://github.com/kyrollos2/CourseSniper/stargazers">
    <img src="https://img.shields.io/github/stars/kyrollos2/CourseSniper.svg?style=flat-square" alt="GitHub stars">
  </a>
  <a href="https://github.com/kyrollos2/CourseSniper/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/kyrollos2/CourseSniper.svg?style=flat-square" alt="GitHub license">
  </a>
</p>

---

## 📖 Table of Contents

- [Introduction](#-introduction)
- [Quick Stats](#-quick-stats)
- [Features](#-features)
- [How It Works](#-how-it-works)
- [Technical Architecture](#-technical-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [See It Live](#-see-it-live)
- [Contributing](#-contributing)
- [Team](#-team)
- [License](#-license)
- [Acknowledgments](#-acknowledgments)

---

## 💡 Introduction

<p align="center">
  <img src="https://github.com/kyrollos2/CourseSniper/blob/main/ScreenRecording2024-01-18at4.32.34AM-ezgif.com-video-to-gif-converter.gif" alt="Project GIF">
</p>

The idea for Course Sniper arose from our school not having an alert system for when a seat opens up in a popular class. Last semester, a required course for my major filled up overnight. The only way I could check for an available seat was by carrying my laptop everywhere, as the college's course catalog is not optimized for mobile browsers.

## 📊 Quick Stats

<p align="center">
  <img src="language_breakdown.png" alt="Language Breakdown">
  <img src="commit_activity.png" alt="Commit Activity">
</p>

- **Languages**: JavaScript, Java, Python, SQL
- **Dependencies**: 5
- **Last Commit**: 2 days ago
- **Active Contributors**: 4

## 🔥 Features

- :mortar_board: **Course Selection**: Easily select multiple courses that you wish to track.
- 🖌️ **Intuitive User Interface**: A simple and easy-to-use interface for everyone.
- 🎯 **Dynamic Updates**: Real-time database updates for the most accurate alerts.
- ⏰ **Instant Notifications**: Get notified the moment a seat becomes available.

## 🧩 How It Works

Course Sniper uses a Python script to navigate to the college's public course catalog. This script scrapes all class data and pushes it to a JSON file. A Java backend service then dumps this JSON into a Google Cloud-based MySQL server. This automated process, when detecting an available seat, sends an alert to students tracking that course.


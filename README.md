# ☕ Java Code Analyzer Pro

[![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.2.0-61dafb.svg)](https://reactjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 🚀 Overview

**Java Code Analyzer Pro** is a comprehensive, AI-powered static code analysis tool that helps developers identify potential bugs, security vulnerabilities, and code quality issues in Java applications. The tool features a modern React frontend and a robust Spring Boot backend with JWT authentication.

### ✨ Key Features

- 🔍 **Real-time Code Analysis** - Instant detection of code issues
- 🛡️ **Security Vulnerability Detection** - SQL injection, hardcoded credentials, and more
- 📊 **Code Metrics Dashboard** - Lines of code, comment coverage, complexity scores
- 🔐 **JWT Authentication** - Secure user authentication and authorization
- 📄 **PDF Report Generation** - Download detailed analysis reports
- 🎨 **Modern UI** - Beautiful, responsive interface with animations
- ⚡ **Performance Analysis** - Detect performance bottlenecks
- 🐛 **Bug Pattern Detection** - Null pointers, resource leaks, concurrency issues

  ## 📋 Table of Contents

- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Usage Guide](#usage-guide)
- [Sample Code](#sample-code)
- [Contributing](#contributing)
- [License](#license)

  ## 🏗️ Architecture
┌─────────────────────────────────────────────────────────────┐
│ Client Browser (Port 3000) │
│ React Frontend App │
└─────────────────────────────────────────────────────────────┘
│
│ HTTP/REST API
▼
┌─────────────────────────────────────────────────────────────┐
│ Spring Boot Backend (Port 8080) │
│ ┌─────────────┐ ┌─────────────┐ ┌──────────────────┐ │
│ │ Auth API │ │ Analysis API│ │ Metrics API │ │
│ └─────────────┘ └─────────────┘ └──────────────────┘ │
│ │
│ ┌─────────────┐ ┌─────────────┐ ┌──────────────────┐ │
│ │ JWT Filter │ │CORS Config │ │ Security Config │ │
│ └─────────────┘ └─────────────┘ └──────────────────┘ │
└─────────────────────────────────────────────────────────────┘
│
│ JPA/Hibernate
▼
┌─────────────────────────────────────────────────────────────┐
│ MySQL / H2 Database │
│ (User Management) │
└─────────────────────────────────────────────────────────────┘


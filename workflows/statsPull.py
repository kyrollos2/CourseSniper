import requests
import matplotlib.pyplot as plt
import seaborn as sns
import datetime
import numpy as np
import pandas as pd

# Fetch language data from GitHub's API
def fetch_languages(user, repo):
    url = f"https://api.github.com/repos/{user}/{repo}/languages"
    response = requests.get(url)
    return response.json() if response.ok else {}

# Enhanced Plot for Language Breakdown
def plot_languages_improved(languages, repo):
    labels = languages.keys()
    sizes = languages.values()
    explode = [0.05] * len(labels)  # Slightly explode all slices

    # Use a seaborn color palette with a black background
    fig, ax = plt.subplots()
    fig.patch.set_facecolor('black')
    ax.set_facecolor('black')
    wedges, texts, autotexts = ax.pie(sizes, labels=labels, autopct='%1.1f%%', startangle=140, colors=sns.color_palette("Set3", len(labels)), shadow=True, explode=explode, pctdistance=0.85)

    # Customizing text color
    for text in texts + autotexts:
        text.set_color('white')
    
    ax.set_title(f'Language Breakdown in {repo}', fontsize=16, fontweight='bold', color='white')
    plt.savefig(f"{repo}_languages_breakdown.png", facecolor=fig.get_facecolor())

# Fetch detailed commit data from GitHub's API
def fetch_detailed_commits(user, repo):
    commits_url = f"https://api.github.com/repos/{user}/{repo}/commits"
    params = {'per_page': 100}  # Fetch up to 100 commits
    response = requests.get(commits_url, params=params)
    return response.json() if response.ok else []


def plot_commit_activity_improved(commits, repo):
    commit_dates = [datetime.datetime.strptime(commit['commit']['committer']['date'], '%Y-%m-%dT%H:%M:%SZ').date() for commit in commits]
    commit_freq = {}
    for date in commit_dates:
        commit_freq[date] = commit_freq.get(date, 0) + 1

    data = pd.DataFrame({'Date': commit_freq.keys(), 'Commits': commit_freq.values()})
    
    plt.figure(figsize=(12, 6))
    ax = sns.barplot(x='Date', y='Commits', data=data, palette="plasma")

    
    ax.figure.set_facecolor('black')
    ax.set_facecolor('black')
    ax.tick_params(colors='white', which='both')  
    ax.xaxis.label.set_color('white')  
    ax.yaxis.label.set_color('white') 
    ax.title.set_color('white')  

    plt.xticks(rotation=45, ha='right')
    plt.xlabel('Date', fontsize=12)
    plt.ylabel('Number of Commits', fontsize=12)
    plt.title(f'Commit Activity for {repo}', fontsize=14, fontweight='bold')

    
    plt.tight_layout()
    plt.savefig(f"{repo}_commit_activity.png", facecolor=ax.figure.get_facecolor())

if __name__ == "__main__":
    user = 'kyrollos2'
    repo = 'CourseSniper'

    languages = fetch_languages(user, repo)
    if languages:
        plot_languages_improved(languages, repo)
    else:
        print("Failed to fetch language data")

    commits = fetch_detailed_commits(user, repo)
    if commits:
        plot_commit_activity_improved(commits, repo)
    else:
        print("Failed to fetch commit data")


from yt_dlp import YoutubeDL

def download(url, out):
    opts = {
        "outtmpl": f"{out}/%(title)s.%(ext)s",
        "format": "bestvideo+bestaudio/best",
        "merge_output_format": "mp4"
    }
    with YoutubeDL(opts) as ydl:
        ydl.download([url])

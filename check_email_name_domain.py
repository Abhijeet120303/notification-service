import subprocess

expectedEmail = "nirphalabhijeet72@gmail.com"
expectedName = "Abhijeet Nirphal"

email = subprocess.run(["git", "config", "user.email"], capture_output=True, text=True).stdout.strip()
name = subprocess.run(["git", "config", "user.name"], capture_output=True, text=True).stdout.strip()

if email == expectedEmail and name == expectedName:
    print("You are using email and name as per configuration, proceeding to commit")
    exit(0)
else:
    print(f"Configured email or name is not as per config. It should be {expectedEmail} and name should be {expectedName}")
    exit(1)
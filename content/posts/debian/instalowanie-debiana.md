+++
title = "Instalowanie Debiana"
date = "2023-04-25T19:20:00+02:00"
author = ""
authorTwitter = "" #do not include @
cover = ""
tags = ["linux", "distro", "instalation", "debian"]
keywords = ["linux", "distro", "debian", "instalation"]
description = "Instalacja dystrybucji debiana"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
+++
# Instalacja
Najlepiej wybrać instalację bez insterfejsu graficznego.

1. Wybrać język, lokalizację oraz układ klawiatury
2. Wybrać hostname (nazwa komputera)
3. Nazwa domenowa (Dla sieci domowej można sobie wymyślić, jednakże wszystkie komputery w sieci powinny mieć takie same)
4. Hasło dla roota
5. Pełna nazwa użytkownika
6. Hasło użytkownika
7. Wybrać strefę czasową
8. Stworzyć partycje na dysku (Można automatycznie lub ręcznie)
9. Wybrać schemat partycjonizowania (Jest takie słowo?) (Najlepiej wybrać żeby wszystkie pliki były zapisywane w jednej partycji)
10. Zakończyć i zatwierdzić zmiany na dysku
11. Wystkoczy zapytanie czy skanować medium instalacyjne do instalowania pakietów (prawdopodobnie)
12. Najlepiej zatwierdzić, żeby dodać "network mirror" i wybrać odpowiedni kraj i "archive mirror"
13. Wybrać co zainstalować (Spacją się "odklikuje", a enterem zatwierdza)

# Po instalacji
Co prawda jest to już pełni funkcjonalny system, można zainstalować dodatkowe pakiety używając apt.
Jednak apt nie działa od razu.
Jest to prawdopodobnie spowodowane tym, że Debian próbuje pobrać repozytoria z płyty CD/DVD, który jest odłączony, ponieważ zostaliśmy o to poproszeni na koniec instalacji.
Można to naprawić usuwając płytę CD/DVD jako repozytorium dla apt.
Jest to zapisane w pliku */etc/apt/sources.list* i wystarczy zakomentować linijkę z wpisem:
`deb cdrom:...`.
Po tej zmianie powinno apt działać prawidłowo.

+++
title = "Pakiet Sudo"
date = "2023-04-25"
lastmod = "2023-04-25"
author = "Szymzal"
authorTwitter = "" #do not include @
cover = ""
tags = ["linux", "sudo"]
keywords = ["sudo"]
description = "Instalacja, konfiguracja oraz ogólne informacje o pakiecie"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
+++
Sudo jest pakietem, który umożliwia na używanie uprawnień konta root za pomocą konta administratorskiego bez znania hasła do konta root.

Pakiet ten jest bardzo powszechny i bardzo często używany w różnych dystrybucjach linuxa.

# Instalacja

Instalowanie tego pakietu jest bardzo prosty:

Dystrybucje oparte na Debian (Ubuntu):
```
apt install sudo
```

# Konfiguracja
Po zainstalowaniu pakietu należy dodać użytkowników do grupy `sudo` jeśli chcemy umożliwić im korzystanie z uprawnień roota.

Nazwa grupy różni się w zależności od dystrybucji. Domyślnie zainstalowana wersja apt korzysta z grupy o nazwie `sudo`.
Jednak pojawiają się nazwy grup takie jak:
- `wheel` (Ubuntu)

Można edytować nazwę grupy, która będzie dodawała taką możliwość w konfiguracji sudo.

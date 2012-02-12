# parse arguments
args <- commandArgs(trailingOnly=TRUE)
report_var <- if (length(args) > 0) args[1] else "Latency"

# define auxiliary vectors
indices <- seq(1,3) # Duration 1, Latency 2, Throughput 3
arg_expected <- c("Duration", "Latency", "Throughput")
arg_expected -> names(indices)
cur_index <- indices[[report_var]]

# read data
report <- read.csv("test_locks_report.csv", check.names=FALSE)

# prepare additional variables
# specs <- levels(report$Spec)
specs <- c("JVM", "JVM_Biased", "JUC")
nspecs <- length(specs)
xrange <- range(report$Number)
yrange <- range(report[[report_var, exact=FALSE]])
ylabs <- names(report)[indices + 2]
yticks <- seq(0, max(report[[report_var, exact=FALSE]], by=5e+7))

# set up the plot
pdf(paste("chart_", tolower(report_var), ".pdf", sep=""))
plot(xrange, yrange, type="n", xlab="Number of threads", ylab=ylabs[cur_index], yaxt="n")
axis(2, las=2)
colors <- rainbow(nspecs)
i <- 1

# add lines
for (spec in specs) {
	specset <- subset(report, Spec == spec)
	lines(specset$Number, specset[[report_var, exact=FALSE]], type="b", col=colors[i])
	i <- i + 1
}

# add title and legend
title("Fucking chart I am pissed of")
legend("topright", inset=.05, c("-UseBiasedLocking", "+UseBiasedLocking", "ReentrantLock"), fill=colors)

# good bye
dev.off()

